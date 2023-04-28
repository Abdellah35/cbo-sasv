package com.cbo.core.service;

import com.cbo.core.exception.*;
import com.cbo.core.model.*;
import com.cbo.core.repo.*;
import com.cbo.core.response.ByRole;
import com.cbo.core.response.RecentUsers;
import com.cbo.core.response.dashboard;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AuthorityDbService {

    private final AuthorityDbRepository authorityRepo;

    private final EmployeeRepository employeeRepository;

    private final DivisionRepository divisionRepository;

    private final UserRepository userRepository;

    private final VisitorRepository visitorRepository;


    public AuthorityDbService(AuthorityDbRepository authorityRepo, EmployeeRepository employeeRepository, DivisionRepository divisionRepository, UserRepository userRepository, VisitorRepository visitorRepository) {
        this.authorityRepo = authorityRepo;
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
        this.userRepository = userRepository;
        this.visitorRepository = visitorRepository;

    }

    public AuthorityDB addAuthority(Long divisionId, Long employeeId) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        Division division = divisionRepository.findDivisionById(divisionId).orElse(null);

        //this checks if employee is not null
        if (employee == null)
            throw new NoSuchUserExistsException("No such employee exists!");
        else if (division == null) {
            //throws exception if division is not present with the provided id
            throw new NoSuchUserExistsException("No such division exists!");
        } else if (employee.getDivision() != null) {
            //throws exception if the division and division of the employee are differ
            if (employee.getDivision().getId() != divisionId)
                throw new NoSuchUserExistsException("This employee is not part of the provided division.");
        }

        AuthorityDB authDb = authorityRepo.findAuthorityDBByDivisionAndIsActive(division, true).orElse(null);
        if (authDb != null) {
            if (authDb.getEmployee().getId() == employeeId) {
                throw new NoSuchUserExistsException("Authority already exist!");
            }
            authDb.setIsActive(false);
            authDb.setUpdatedAt(dtf.format(now));
            authorityRepo.save(authDb);
        }

        if (employee != null) {

            if ((employee.getSignatureImage() == null))
                throw new ImageNotFoundException("Signature Image is not found for this employee.");
            else if ((division.getStampImage() == null))
                throw new ImageNotFoundException("Stamp Image is not found for this division.");
            else {

                AuthorityDB authorityD = new AuthorityDB(division, employee, true);
                authorityD.setDivision(division);
                authorityD.setEmployee(employee);
                authorityD.setIsActive(true);
                authorityD.setCreatedAt(dtf.format(now));
                AuthorityDB newauthority = authorityRepo.save(authorityD);
                return newauthority;
            }

        } else
            throw new NoSuchUserExistsException("No such employee exists!");

    }


    public List<AuthorityDB> findAllAuthority() {

        return authorityRepo.findAll();
    }


    public List<AuthorityDB> findAllAuthorityByState(boolean isActive) {

        return authorityRepo.findAllByIsActive(isActive);
    }


    public AuthorityDB updateAuthority(Long id, Boolean isActive) {

        AuthorityDB oldAuthority = authorityRepo.findById(id).orElse(null);
        if (oldAuthority == null)
            throw new NoSuchUserExistsException("No such authority exists!");
        else {
            oldAuthority.setIsActive(isActive);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            oldAuthority.setUpdatedAt(dtf.format(now));

            AuthorityDB auth0 = authorityRepo.save(oldAuthority);

            return auth0;
        }
    }


    public AuthorityDB findAuthorityById(Long id) {
        return authorityRepo.findById(id).orElseThrow(() -> new NoSuchUserExistsException("This Authority not found"));
    }

    public String deleteAuthority(Long id) {

        AuthorityDB oldAuthority = authorityRepo.findById(id).orElse(null);
        if (oldAuthority == null)
            throw new NoSuchUserExistsException("No such authority exists!");
        else {
            authorityRepo.deleteById(id);

            return "Record deleted Successfully";
        }
    }

    public dashboard getdashData() {

        dashboard dsdata = new dashboard();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        String[] result = dtf.format(now).split("/");

        //users joined today
        List<Integer> newUsers = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(result[2]); i++) {
            String day = Integer.toString(i+1);
            if(i < 9)
                day = 0 + Integer.toString(i+1);
            String datecn = result[0] + "/" + result[1] + "/" + day;
            List<User> newUser = userRepository.findByMonth(datecn);
            newUsers.add(newUser.size());
        }
        dsdata.setNewUsers(newUsers);

        //All Users
        List<User> allUser = userRepository.findAll();
        dsdata.setAllUsers(allUser.size());

        //all active authorities
        List<AuthorityDB> allactiveAuth = findAllAuthorityByState(true);
        dsdata.setActiveAuth(allactiveAuth.size());

        //all Authorities
        List<AuthorityDB> allAuth = findAllAuthority();
        dsdata.setAllAuth(allAuth.size());

        //today visited page
        List<Visitor> vs = visitorRepository.findByMonth(dtf.format(now));
        dsdata.setPageViewToday(vs.size());
        String role;
        int admin = 0;
        int nuser = 0;
        int directr = 0;
        for (int j = vs.size(); j > 0; j--) {
            if (vs.get(j - 1).getAppUser() != null) {

                role = vs.get(j - 1).getAppUser().split(",")[2].split(":")[2].replace("\"", "").replace("}]","");

                if(role.equals("ROLE_ADMIN")){
                    admin += 1;
                } else if (role.equals("ROLE_DIRECTOR")) {
                    directr += 1;
                }else{
                    nuser += 1;
                }
            }

        }

        dsdata.setRoleVisit(new ByRole(admin,directr,nuser));

        //today logged users
        List<Visitor> uniqueUsers = visitorRepository.findByVisitors(dtf.format(now), "/auth/login");
        dsdata.setTodayLogin(uniqueUsers.size());

        //number of all Employees
        List<Employee> allEmployee = employeeRepository.findAll();
        dsdata.setAllEmployee(allEmployee.size());

        //number of female employees
        List<Employee> femaleEmployee = employeeRepository.findEmployeeByGender("Female");
        dsdata.setFemaleUsers(femaleEmployee.size());

        //number of all divisions
        List<Division> divisions = divisionRepository.findAll();
        dsdata.setAllDivision(divisions.size());
        Set<RecentUsers> recUsers = new LinkedHashSet<RecentUsers>();
        Set<String> unique = new LinkedHashSet<String>();
        // recently logged-in users
        for (int i = uniqueUsers.size(); i > 0; i--) {

            if (recUsers.size() <= 5) {
                List<Visitor> visitorUsers = visitorRepository.findByIps(dtf.format(now), uniqueUsers.get(i - 1).getIp());
                for (int j = visitorUsers.size(); j > 0; j--) {
                    if (visitorUsers.get(j - 1).getAppUser() != null) {
                        if(!unique.contains(visitorUsers.get(j - 1).getAppUser().split(",")[1].split(":")[1])){
                            if (recUsers.size() <= 5) {
                                System.out.println(visitorUsers.get(j - 1).getLoggedTimetime());
                                recUsers.add(new RecentUsers(
                                        visitorUsers.get(j - 1).getAppUser().split(",")[1].split(":")[1].replace("\"","" ),
                                        visitorUsers.get(j-1).getLoggedTimetime(),
                                        visitorUsers.get(j - 1).getAppUser().split(",")[2].split(":")[2].replace("\"", "").replace("}]","")));
                            } else {
                                break;
                            }
                        }
                        unique.add(visitorUsers.get(j - 1).getAppUser().split(",")[1].split(":")[1]);


                    }
                }
            } else {
                break;
            }
        }

        dsdata.setRecentUsers(recUsers);
        return dsdata;
    }
}