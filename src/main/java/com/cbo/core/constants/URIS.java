package com.cbo.core.constants;

public class URIS {

    private static final String APPLICATION_CONTEXT = "sasv";

    private static final String AUTHORITY = APPLICATION_CONTEXT + "/authority/";
    public static final String AUTHORITY_REGISTER = AUTHORITY + "register";
    public static final String AUTHORITY_UPDATE = AUTHORITY + "update";
    public static final String AUTHORITY_LIST_ALL = AUTHORITY + "listAll";
    public static final String AUTHORITY_LIST_ACTIVE = AUTHORITY + "listActive";
    public static final String AUTHORITY_DELETE = AUTHORITY + "delete";

    public static final String AUTHORITY_BY_ID = AUTHORITY + "byId";

    public static final String AUTHORITY_IMAGES_BY_ID = AUTHORITY + "images/{id}";

    public static final String SIGNATURE_IMAGES_BY_ID = AUTHORITY + "images/signature/{signatureId}";
    public static final String SIGNATURE_IMAGES_BY_EMPLOYEE_ID = AUTHORITY + "images/signature/employee/{employeeId}";
    public static final String STAMP_IMAGES_BY_ID = AUTHORITY + "images/stamp/{stampId}";

    public static final String EMPLOYEE_LIST = AUTHORITY + "employees";
    public static final String EMPLOYEE_BY_ID = AUTHORITY + "employees/{employeeId}";
    public static final String EMPLOYEE_BY_NAME = AUTHORITY + "employees/byName";
    public static final String ORG_UNIT_LIST = AUTHORITY + "OrgUnits";
    public static final String ORG_UNIT_BY_ID = AUTHORITY + "OrgUnits/{orgUnitId}";

    public static final String Sub_PROCESS_LIST = AUTHORITY + "subProcesses";
    public static final String Sub_PROCESS_BY_ID = AUTHORITY + "subProcesses/{subProcessId}";
    public static final String EMPLOYEE_BY_SUB_PROCESS_ID = AUTHORITY + "subProcesses/{subProcessId}/employees";

    public static final String PROCESS_LIST = AUTHORITY + "processes";
    public static final String PROCESS_BY_ID = AUTHORITY + "processes/{processId}";
    public static final String EMPLOYEE_BY_PROCESS_ID = AUTHORITY + "processes/{processId}/employees";



    public static final String BRANCH_LIST = AUTHORITY + "branches";
    public static final String BRANCH_BY_ID = AUTHORITY + "branches/{branchId}";

    public static final String TEAM_LIST = AUTHORITY + "teams";
    public static final String TEAM_BY_ID = AUTHORITY + "teams/{teamId}";

    public static final String DISTRICT_LIST = AUTHORITY + "districts";
    public static final String DISTRICT_BY_ID = AUTHORITY + "districts/{districtId}";

    public static final String UPLOAD_SIGNATURE = AUTHORITY + "signature";

    public static final String UPLOAD_STAMP = AUTHORITY + "stamp";

    public static final String RETRIEVE_SIGNATURE_LIST = AUTHORITY + "signature/all";

    public static final String RETRIEVE_STAMP_LIST = AUTHORITY + "stamp/all";

}
