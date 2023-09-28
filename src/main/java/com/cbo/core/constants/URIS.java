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


    public static final String EMPLOYEE_LIST = AUTHORITY + "employees";
    public static final String EMPLOYEE_BY_ID = AUTHORITY + "employees/{employeeId}";
    public static final String ORG_UNIT_LIST = AUTHORITY + "OrgUnits";
    public static final String ORG_UNIT_BY_ID = AUTHORITY + "OrgUnits/{orgUnitId}";

    public static final String Sub_PROCESS_LIST = AUTHORITY + "subProcesses";
    public static final String Sub_PROCESS_BY_ID = AUTHORITY + "subProcesses/{subProcessId}";

    public static final String PROCESS_LIST = AUTHORITY + "processes";
    public static final String PROCESS_BY_ID = AUTHORITY + "processes/{processId}";

    public static final String UPLOAD_SIGNATURE = AUTHORITY + "signature";

    public static final String UPLOAD_STAMP = AUTHORITY + "stamp";

    public static final String RETRIEVE_SIGNATURE_LIST = AUTHORITY + "signature/all";

    public static final String RETRIEVE_STAMP_LIST = AUTHORITY + "stamp/all";

}
