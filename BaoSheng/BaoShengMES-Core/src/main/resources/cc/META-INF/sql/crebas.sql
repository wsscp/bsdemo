/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2014/11/17 10:19:42                          */
/*==============================================================*/


alter table T_BAS_BIZ_INFO
   drop constraint FK_BAS_BIZ_ENTITY_TYPE;

alter table T_BAS_BIZ_LOG
   drop constraint FK_BAS_BIZ_LOG_RE_BIZ_TYPE;

alter table T_BAS_EQIP_CAL_SHIFT
   drop constraint FK_EQCA_SHIFT_EQCA_ID;

alter table T_BAS_EQIP_CAL_SHIFT
   drop constraint FK_EQCA_SHIFT_SHIFT_ID;

alter table T_BAS_MES_CLIENT_MAN_EQIP
   drop constraint FK_MES_CLI_MAN_EQIP_CLI_ID;

alter table T_BAS_MES_CLIENT_MAN_EQIP
   drop constraint FK_MES_CLI_MAN_EQIP_EQIP_ID;

alter table T_BAS_ROLE_EQIP
   drop constraint FK_ROLE_EQIP_EQIP_ID;

alter table T_BAS_ROLE_EQIP
   drop constraint FK_ROLE_EQIP_ROLE_ID;

alter table T_BAS_ROLE_RESOURCE
   drop constraint FK_T_BAS_RO_FK_RESOUR_T_BAS_RE;

alter table T_BAS_ROLE_RESOURCE
   drop constraint FK_T_BAS_RO_FK_ROLE_R_T_BAS_RO;

alter table T_BAS_USER_ROLE
   drop constraint FK_T_BAS_US_FK_ROLE_U_T_BAS_RO;

alter table T_BAS_USER_ROLE
   drop constraint FK_T_BAS_US_FK_USER_U_T_BAS_US;

alter table T_BAS_WEEK_CALENDAR_SHIFT
   drop constraint FK_WECA_SHIFT_SHIFT_ID;

alter table T_BAS_WEEK_CALENDAR_SHIFT
   drop constraint FK_WECA_SHIFT_WECA_ID;

alter table T_EVE_EVENT_INFO
   drop constraint FK_EVENT_EVE_TYPE_ID;

alter table T_EVE_EVENT_PROCESS
   drop constraint FK_EVN_PROC_EVN_PROC_TYPE;

alter table T_EVE_EVENT_PROCESSER
   drop constraint FK_EV_PROER_EVE_PROCE_ID;

alter table T_FAC_EQUIP_MAINTAIN_STATE
   drop constraint FK_T_FAC_EQ_REFERENCE_T_EVE_EV;

alter table T_FAC_MAINTAIN_ITEM
   drop constraint FK_T_FAC_MA_FK_ITEM_T_T_FAC_MA;

alter table T_FAC_MAINTAIN_RECORD
   drop constraint FK_T_FAC_MA_REFERENCE_T_FAC_MA;

alter table T_FAC_MAINTAIN_RECORD_ITEM
   drop constraint FK_T_FAC_MA_FK_ITEM_R_T_FAC_MA;

alter table T_INV_INVENTORY
   drop constraint FK_INV_LOCATION_ID;

alter table T_INV_INVENTORY
   drop constraint FK_INV_WAREHOUSE_ID;

alter table T_INV_INVENTORY_DETAIL
   drop constraint FK_INV_DETAIL_INV_ID;

alter table T_INV_LOCATION
   drop constraint FK_LOCATION_WAREHOUSE_ID;

alter table T_INV_MAT
   drop constraint FK_MAT_MAT_TEMPLET_ID;

alter table T_INV_MAT_PROP
   drop constraint FK_MAT_PROP_MAT_ID;

alter table T_INV_MAT_PROP
   drop constraint FK_MAT_PROP_MAT_TEMP_DETA_ID;

alter table T_INV_TEMPLET_DETAIL
   drop constraint FK_TEMPLE_DETAIL_TEMPLE_ID;

alter table T_ORD_SALES_ORDER_ITEM
   drop constraint FK_SALE_ORD_ITEM_SALE_ORDER_ID;

alter table T_PLA_CUSTOMER_ORDER
   drop constraint FK_CU_ORDER_SA_ORDER_ID;

alter table T_PLA_CUSTOMER_ORDER_ITEM
   drop constraint FK_CU_ORDER_ITEM_SA_OR_ITEM_ID;

alter table T_PLA_CUSTOMER_ORDER_ITEM
   drop constraint FK_CU_ORD_ITEM_CU_ORD_ID;

alter table T_PLA_CUSTOMER_ORDER_ITEM_DEC
   drop constraint FK_CU_ORDER_DEC_CU_ORDER_ID;

alter table T_PLA_CU_ORDER_ITEM_PRO_DEC
   drop constraint FK_CU_OR_ITEM_PRO_DEC_ID;

alter table T_PLA_HIGH_PRIORITY_ORDER_ITEM
   drop constraint FK_PLA_HI_PRI_PLA_ORDER;

alter table T_PLA_HIGH_PRIORITY_PRO_DEC
   drop constraint FK_T_PLA_HI_REFERENCE_T_PLA_CU;

alter table T_PLA_MRP
   drop constraint FK_MRP_WO_ORD_ID_WO_ORD;

alter table T_PLA_ORDER_TASK
   drop constraint FK_ORDER_TASK_PRO_DEC_ID;

alter table T_PLA_TOOLES_RP
   drop constraint FK_TOOLES_RP_ID_WO;

alter table T_PRO_EQIP_LIST
   drop constraint FK_PRO_EQIP_LIST_PRO_ID;

alter table T_PRO_PROCESS_IN_OUT
   drop constraint FK_PRO_INOUT_PRO_PROCESS_ID;

alter table T_PRO_PROCESS_QC
   drop constraint FK_PROCESS_QC_PROCESS_ID;

alter table T_PRO_PROCESS_QC_EQIP
   drop constraint FK_T_PRO_PR_REFERENCE_T_PRO_PR;

alter table T_PRO_PROCESS_RECEIPT
   drop constraint FK_PRO_RECP_PRO_ID;

alter table T_PRO_PRODUCT_PROCESS
   drop constraint FK_PRO_PORCESS_PRO_CRAFTS_ID;

alter table T_PRO_PRODUCT_QC_DET
   drop constraint FK_PRO_QC_DE_REF_QC_TEMP;

alter table T_PRO_PRODUCT_QC_DET
   drop constraint FK_QC_DE_REF_PRO_QC_RE;

drop view V_PLAN_DEATIL;

drop view V_PROCESS_EQUIP;

drop table T_BAS_ACTION_LOG cascade constraints;

drop table T_BAS_ATTACHMENT cascade constraints;

drop table T_BAS_BIZ_INFO cascade constraints;

drop table T_BAS_BIZ_LOG cascade constraints;

drop table T_BAS_DATA_DICT cascade constraints;

drop table T_BAS_EMPLOYEE cascade constraints;

drop table T_BAS_ENTITY_INFO cascade constraints;

drop table T_BAS_EQIP_CALENDAR cascade constraints;

drop table T_BAS_EQIP_CAL_SHIFT cascade constraints;

drop table T_BAS_MES_CLIENT cascade constraints;

drop table T_BAS_MES_CLIENT_MAN_EQIP cascade constraints;

drop table T_BAS_MONTH_CALENDAR cascade constraints;

drop table T_BAS_ORG cascade constraints;

drop table T_BAS_PROPERTIES cascade constraints;

drop table T_BAS_RESOURCE cascade constraints;

drop table T_BAS_ROLE cascade constraints;

drop table T_BAS_ROLE_EQIP cascade constraints;

drop table T_BAS_ROLE_RESOURCE cascade constraints;

drop table T_BAS_SYSTEM_PARAM_CONFIG cascade constraints;

drop table T_BAS_SYS_MESSAGE cascade constraints;

drop table T_BAS_USER cascade constraints;

drop table T_BAS_USER_ROLE cascade constraints;

drop table T_BAS_WEEK_CALENDAR cascade constraints;

drop table T_BAS_WEEK_CALENDAR_SHIFT cascade constraints;

drop table T_BAS_WORK_SHIFT cascade constraints;

drop table T_EVE_EVENT_INFO cascade constraints;

drop table T_EVE_EVENT_PROCESS cascade constraints;

drop table T_EVE_EVENT_PROCESSER cascade constraints;

drop table T_EVE_EVENT_PROCESS_LOG cascade constraints;

drop table T_EVE_EVENT_TYPE cascade constraints;

drop table T_EVE_EVENT_OWNER cascade constraints;

drop table T_EVE_LAST_EXECUTE_TIME_RECORD cascade constraints;

drop table T_EVE_TRIGGER_LOG cascade constraints;

drop table T_FAC_EQIP_INFO cascade constraints;

drop table T_FAC_EQUIP_MAINTAIN_STATE cascade constraints;

drop table T_FAC_MAINTAIN_ITEM cascade constraints;

drop table T_FAC_MAINTAIN_RECORD cascade constraints;

drop table T_FAC_MAINTAIN_RECORD_ITEM cascade constraints;

drop table T_FAC_MAINTAIN_TEMPLATE cascade constraints;

drop table T_FAC_PRODUCT_EQIP cascade constraints;

drop table T_FAC_STATUS_HISTORY cascade constraints;

drop index IDX_FAC_WORK_TASKS_START_TIME;

drop table T_FAC_WORK_TASKS cascade constraints;

drop table T_INT_EQIP_ISSUE_PARMS cascade constraints;

drop table T_INT_EQIP_SIGN cascade constraints;

drop table T_INT_EQUIP_MES_WW_MAPPING cascade constraints;

drop table T_INT_EMAIL_MESSAGE cascade constraints;

drop index INDEX_INVENTORY_MATERIAL_CODE;

drop table T_INV_INVENTORY cascade constraints;

drop table T_INV_INVENTORY_DETAIL cascade constraints;

drop table T_INV_INVENTORY_LOG cascade constraints;

drop table T_INV_LOCATION cascade constraints;

drop table T_INV_MAT cascade constraints;

drop table T_INV_MAT_PROP cascade constraints;

drop table T_INV_TEMPLET cascade constraints;

drop table T_INV_TEMPLET_DETAIL cascade constraints;

drop table T_INV_WAREHOUSE cascade constraints;

drop table T_JOB_CONFIG cascade constraints;

drop table T_JOB_LOG cascade constraints;

drop table T_ORD_OA_CHANGE_HIS cascade constraints;

drop table T_ORD_SALES_ORDER cascade constraints;

drop table T_ORD_SALES_ORDER_ITEM cascade constraints;

drop table T_PLA_CUSTOMER_ORDER cascade constraints;

drop table T_PLA_CUSTOMER_ORDER_ITEM cascade constraints;

drop table T_PLA_CUSTOMER_ORDER_ITEM_DEC cascade constraints;

drop table T_PLA_CU_ORDER_ITEM_PRO_DEC cascade constraints;

drop table T_PLA_HIGH_PRIORITY_ORDER_ITEM cascade constraints;

drop table T_PLA_HIGH_PRIORITY_PRO_DEC cascade constraints;

drop table T_PLA_INV_OA_USE_LOG cascade constraints;

drop table T_PLA_MRP cascade constraints;

drop table T_PLA_MRP_OA cascade constraints;

drop index IDX_PLA_ORD_TASK_PLANSTARTDATE;

drop index INDEX_ORDER_TASK_PRO_DEC_ID;

drop table T_PLA_ORDER_TASK cascade constraints;

drop table T_PLA_PRODUCT cascade constraints;

drop table T_PLA_PRODUCT_SOP cascade constraints;

drop table T_PLA_TOOLES_RP cascade constraints;

drop table T_PRO_EQIP_LIST cascade constraints;

drop table T_PRO_PROCESS_INFO cascade constraints;

drop table T_PRO_PROCESS_IN_OUT cascade constraints;

drop table T_PRO_PROCESS_QC cascade constraints;

drop table T_PRO_PROCESS_QC_EQIP cascade constraints;

drop table T_PRO_PROCESS_QC_VALUE cascade constraints;

drop table T_PRO_PROCESS_RECEIPT cascade constraints;

drop table T_PRO_PROCESS_RECEIPT_VALUE cascade constraints;

drop table T_PRO_PRODUCT_CRAFTS cascade constraints;

drop table T_PRO_PRODUCT_PROCESS cascade constraints;

drop table T_PRO_PRODUCT_QC_DET cascade constraints;

drop table T_PRO_PRODUCT_QC_RES cascade constraints;

drop table T_PRO_PRODUCT_QC_TEMP cascade constraints;

drop table T_WIP_DEBUG cascade constraints;

drop table T_WIP_ONOFF_RECORD cascade constraints;

drop table T_WIP_REAL_COST cascade constraints;

drop table T_WIP_RECEIPT cascade constraints;

drop table T_WIP_REPORT cascade constraints;

drop table T_WIP_SCRAP cascade constraints;

drop table T_WIP_SPARK_REPAIR cascade constraints;

drop index INDEX_WIP_SECION_PRO_DEC_ID;

drop table T_WIP_SECTION cascade constraints;

drop index INDEX_WORK_ORDER_ORNO;

drop table T_WIP_WORK_ORDER cascade constraints;

drop table T_WIP_WORK_ORDER_OPERATE_LOG cascade constraints;

drop table T_WIP_WORK_ORDER_REPORT cascade constraints;

drop table T_WIP_WORTH cascade constraints;


/*==============================================================*/
/* Table: T_BAS_ACTION_LOG                                      */
/*==============================================================*/
create table T_BAS_ACTION_LOG 
(
   ID                   VARCHAR2(50)         not null,
   USER_CODE            VARCHAR2(50),
   USER_NAME            VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   ORG_NAME             VARCHAR2(300),
   SERVER_NAME          VARCHAR2(50),
   SERVER_ADDR          VARCHAR2(50),
   CLIENT_NAME          VARCHAR2(50),
   CLIENT_ADDR          VARCHAR2(50),
   CLIENT_MAC           VARCHAR2(1000),
   CLIENT_USER_AGENT    VARCHAR2(1000),
   SESSION_ID           VARCHAR2(50),
   LOCALE               VARCHAR2(50),
   REQUEST_TIME         TIMESTAMP,
   RESPONSE_TIME        TIMESTAMP,
   APP_ID               VARCHAR2(50),
   MENU_ID              VARCHAR2(50),
   URL                  VARCHAR2(1000),
   METHOD               VARCHAR2(50),
   PARAMS               VARCHAR2(4000),
   ACTION_CLASS         VARCHAR2(200),
   ACTION_METHOD        VARCHAR2(50),
   ACTION_RESULT        VARCHAR2(4000),
   IS_EXCEPTION         CHAR(1),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   constraint PK_BAS_ACTION_LOG_ID primary key (ID)
);

comment on table T_BAS_ACTION_LOG is
'系统访问日志';

comment on column T_BAS_ACTION_LOG.ID is
'ID';

comment on column T_BAS_ACTION_LOG.USER_CODE is
'用户工号';

comment on column T_BAS_ACTION_LOG.USER_NAME is
'用户姓名';

comment on column T_BAS_ACTION_LOG.ORG_CODE is
'组织CODE';

comment on column T_BAS_ACTION_LOG.ORG_NAME is
'组织NAME';

comment on column T_BAS_ACTION_LOG.SERVER_NAME is
'服务器NAME';

comment on column T_BAS_ACTION_LOG.SERVER_ADDR is
'服务器IP';

comment on column T_BAS_ACTION_LOG.CLIENT_NAME is
'客户端NAME';

comment on column T_BAS_ACTION_LOG.CLIENT_ADDR is
'客户端地址';

comment on column T_BAS_ACTION_LOG.CLIENT_MAC is
'客户端MAC';

comment on column T_BAS_ACTION_LOG.CLIENT_USER_AGENT is
'客户端代理';

comment on column T_BAS_ACTION_LOG.SESSION_ID is
'会话ID';

comment on column T_BAS_ACTION_LOG.LOCALE is
'语言';

comment on column T_BAS_ACTION_LOG.REQUEST_TIME is
'请求时间';

comment on column T_BAS_ACTION_LOG.RESPONSE_TIME is
'响应时间';

comment on column T_BAS_ACTION_LOG.APP_ID is
'应用程序ID';

comment on column T_BAS_ACTION_LOG.MENU_ID is
'菜单ID';

comment on column T_BAS_ACTION_LOG.URL is
'链接';

comment on column T_BAS_ACTION_LOG.METHOD is
'方法';

comment on column T_BAS_ACTION_LOG.PARAMS is
'参数';

comment on column T_BAS_ACTION_LOG.ACTION_CLASS is
'动作类';

comment on column T_BAS_ACTION_LOG.ACTION_METHOD is
'动作方法';

comment on column T_BAS_ACTION_LOG.ACTION_RESULT is
'动作结果';

comment on column T_BAS_ACTION_LOG.IS_EXCEPTION is
'是否异常';

comment on column T_BAS_ACTION_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ACTION_LOG.CREATE_TIME is
'创建时间';

comment on column T_BAS_ACTION_LOG.MODIFY_TIME is
'更新时间';

comment on column T_BAS_ACTION_LOG.MODIFY_USER_CODE is
'更新人';

/*==============================================================*/
/* Table: T_BAS_ATTACHMENT                                      */
/*==============================================================*/
create table T_BAS_ATTACHMENT 
(
   ID                   VARCHAR2(50)         not null,
   FILE_NAME            VARCHAR2(300)        not null,
   REAL_FILE_NAME       VARCHAR2(300),
   OWNER_MODULE         VARCHAR2(50)         not null,
   CONTENT_TYPE         VARCHAR2(50),
   CONTENT_LENGTH       NUMERIC(12, 0),
   DOWNLOAD_PATH        VARCHAR2(2048),
   REF_ID               VARCHAR2(50)         not null,
   SUB_TYPE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_ATTACHMENT_ID primary key (ID)
);

comment on table T_BAS_ATTACHMENT is
'附件信息表';

comment on column T_BAS_ATTACHMENT.ID is
'ID';

comment on column T_BAS_ATTACHMENT.FILE_NAME is
'文件名';

comment on column T_BAS_ATTACHMENT.REAL_FILE_NAME is
'物理文件名';

comment on column T_BAS_ATTACHMENT.OWNER_MODULE is
'模块';

comment on column T_BAS_ATTACHMENT.CONTENT_TYPE is
'文件类型';

comment on column T_BAS_ATTACHMENT.CONTENT_LENGTH is
'文件大小（K）';

comment on column T_BAS_ATTACHMENT.DOWNLOAD_PATH is
'文件下载路径';

comment on column T_BAS_ATTACHMENT.REF_ID is
'引用数据ID';

comment on column T_BAS_ATTACHMENT.SUB_TYPE is
'附件分类适用于一条主记录对应多个不同的附件，而且使用方式不一致的情况';

comment on column T_BAS_ATTACHMENT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ATTACHMENT.CREATE_TIME is
'创建时间';

comment on column T_BAS_ATTACHMENT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ATTACHMENT.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_BIZ_INFO                                        */
/*==============================================================*/
create table T_BAS_BIZ_INFO 
(
   ID                   VARCHAR2(50)         not null,
   ENTITY_ID            VARCHAR2(50)         not null,
   APP_ID               VARCHAR2(50),
   BIZ_NAME             VARCHAR2(200),
   BIZ_DESC             VARCHAR2(200),
   BIZ_CLASS            VARCHAR2(400),
   BIZ_METHOD           VARCHAR2(50),
   STATUS               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_BAS_BIZ_INFO_ID primary key (ID)
);

comment on table T_BAS_BIZ_INFO is
'系统实体业务定义';

comment on column T_BAS_BIZ_INFO.ID is
'ID';

comment on column T_BAS_BIZ_INFO.ENTITY_ID is
'实体ID';

comment on column T_BAS_BIZ_INFO.APP_ID is
'应用程序ID';

comment on column T_BAS_BIZ_INFO.BIZ_NAME is
'业务名称';

comment on column T_BAS_BIZ_INFO.BIZ_DESC is
'业务描述';

comment on column T_BAS_BIZ_INFO.BIZ_CLASS is
'业务类';

comment on column T_BAS_BIZ_INFO.BIZ_METHOD is
'业务方法';

comment on column T_BAS_BIZ_INFO.STATUS is
'状态';

comment on column T_BAS_BIZ_INFO.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_BIZ_INFO.CREATE_TIME is
'创建时间';

comment on column T_BAS_BIZ_INFO.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_BIZ_INFO.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_BIZ_LOG                                         */
/*==============================================================*/
create table T_BAS_BIZ_LOG 
(
   ID                   VARCHAR2(50)         not null,
   ACTION_ID            VARCHAR2(50)         not null,
   BIZ_ID               VARCHAR2(50)         not null,
   BIZ_TIME             TIMESTAMP            not null,
   USER_CODE            VARCHAR2(50),
   USER_NAME            VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   ORG_NAME             VARCHAR2(200),
   ENTITY_ID            VARCHAR2(50),
   ENTITY_CODE          VARCHAR2(50),
   ENTITY_NAME          VARCHAR2(200),
   IS_EXCEPTION         CHAR(1)              not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_BAS_BIZ_LOG_ID primary key (ID)
);

comment on table T_BAS_BIZ_LOG is
'系统业务日志';

comment on column T_BAS_BIZ_LOG.ID is
'ID';

comment on column T_BAS_BIZ_LOG.ACTION_ID is
'系统访问ID';

comment on column T_BAS_BIZ_LOG.BIZ_ID is
'业务ID';

comment on column T_BAS_BIZ_LOG.BIZ_TIME is
'时间';

comment on column T_BAS_BIZ_LOG.USER_CODE is
'用户工号';

comment on column T_BAS_BIZ_LOG.USER_NAME is
'用户姓名';

comment on column T_BAS_BIZ_LOG.ORG_CODE is
'组织CODE';

comment on column T_BAS_BIZ_LOG.ORG_NAME is
'组织名称';

comment on column T_BAS_BIZ_LOG.ENTITY_ID is
'实体ID';

comment on column T_BAS_BIZ_LOG.ENTITY_CODE is
'实体CODE';

comment on column T_BAS_BIZ_LOG.ENTITY_NAME is
'实体名称';

comment on column T_BAS_BIZ_LOG.IS_EXCEPTION is
'是否异常';

comment on column T_BAS_BIZ_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_BIZ_LOG.CREATE_TIME is
'创建时间';

comment on column T_BAS_BIZ_LOG.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_BIZ_LOG.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_DATA_DICT                                       */
/*==============================================================*/
create table T_BAS_DATA_DICT 
(
   ID                   VARCHAR2(50)         not null,
   TERMS_CODE           VARCHAR2(50),
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   SEQ                  NUMERIC(10),
   LAN                  VARCHAR2(50),
   EXTATT               VARCHAR2(200),
   MARKS                VARCHAR2(4000),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   CAN_MODIFY           CHAR(1)              default '1',
   constraint PK_BAS_DATA_DICT_ID primary key (ID)
);

comment on table T_BAS_DATA_DICT is
'数据字典';

comment on column T_BAS_DATA_DICT.ID is
'ID';

comment on column T_BAS_DATA_DICT.TERMS_CODE is
'词条类型CODE';

comment on column T_BAS_DATA_DICT.CODE is
'词条CODE';

comment on column T_BAS_DATA_DICT.NAME is
'词条名称';

comment on column T_BAS_DATA_DICT.SEQ is
'顺序号';

comment on column T_BAS_DATA_DICT.LAN is
'语言';

comment on column T_BAS_DATA_DICT.EXTATT is
'扩展属性';

comment on column T_BAS_DATA_DICT.MARKS is
'备注';

comment on column T_BAS_DATA_DICT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_DATA_DICT.CREATE_TIME is
'创建时间';

comment on column T_BAS_DATA_DICT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_DATA_DICT.MODIFY_TIME is
'更新时间';

comment on column T_BAS_DATA_DICT.STATUS is
'数据状态';

comment on column T_BAS_DATA_DICT.CAN_MODIFY is
'是否可以修改';

/*==============================================================*/
/* Table: T_BAS_EMPLOYEE                                        */
/*==============================================================*/
create table T_BAS_EMPLOYEE 
(
   ID                   VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   USER_CODE            VARCHAR2(50)         not null,
   ORG_CODE             VARCHAR2(50)         not null,
   TOP_ORG_CODE         VARCHAR2(50)         not null,
   TELEPHONE            VARCHAR2(50),
   EMAIL                VARCHAR2(100),
   SOURCES              VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   CERTIFICATE          VARCHAR2(50),
   constraint PK_BAS_EMPLOYEE primary key (ID)
);

comment on table T_BAS_EMPLOYEE is
'员工信息表';

comment on column T_BAS_EMPLOYEE.ID is
'员工ID';

comment on column T_BAS_EMPLOYEE.NAME is
'员工姓名';

comment on column T_BAS_EMPLOYEE.USER_CODE is
'员工号';

comment on column T_BAS_EMPLOYEE.ORG_CODE is
'直属机构编号';

comment on column T_BAS_EMPLOYEE.TOP_ORG_CODE is
'顶层机构编号';

comment on column T_BAS_EMPLOYEE.TELEPHONE is
'电话';

comment on column T_BAS_EMPLOYEE.EMAIL is
'电子邮件';

comment on column T_BAS_EMPLOYEE.SOURCES is
'数据来源';

comment on column T_BAS_EMPLOYEE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_EMPLOYEE.CREATE_TIME is
'创建时间';

comment on column T_BAS_EMPLOYEE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_EMPLOYEE.MODIFY_TIME is
'更新时间';

comment on column T_BAS_EMPLOYEE.CERTIFICATE is
'人员资质';

/*==============================================================*/
/* Table: T_BAS_ENTITY_INFO                                     */
/*==============================================================*/
create table T_BAS_ENTITY_INFO 
(
   ID                   VARCHAR2(50)         not null,
   ENTITY_TYPE          VARCHAR2(50)         not null,
   ENTITY_CLASS         VARCHAR2(200),
   ENTITY_NAME          VARCHAR2(200),
   ENTITY_DESC          VARCHAR2(200),
   CODE_PROPERTY        VARCHAR2(50),
   VIEW_URL             VARCHAR2(400),
   APP_ID               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_BAS_ENTITY_INFO_ID primary key (ID)
);

comment on table T_BAS_ENTITY_INFO is
'系统实体定义';

comment on column T_BAS_ENTITY_INFO.ID is
'ID';

comment on column T_BAS_ENTITY_INFO.ENTITY_TYPE is
'实体类型';

comment on column T_BAS_ENTITY_INFO.ENTITY_CLASS is
'实体类';

comment on column T_BAS_ENTITY_INFO.ENTITY_NAME is
'实体名';

comment on column T_BAS_ENTITY_INFO.ENTITY_DESC is
'实体描述';

comment on column T_BAS_ENTITY_INFO.CODE_PROPERTY is
'代码';

comment on column T_BAS_ENTITY_INFO.VIEW_URL is
'查看URL';

comment on column T_BAS_ENTITY_INFO.APP_ID is
'应用程序ID';

comment on column T_BAS_ENTITY_INFO.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ENTITY_INFO.CREATE_TIME is
'创建时间';

comment on column T_BAS_ENTITY_INFO.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ENTITY_INFO.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_EQIP_CALENDAR                                   */
/*==============================================================*/
create table T_BAS_EQIP_CALENDAR 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   DATE_OF_WORK         VARCHAR2(8),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_BAS_EQIP_CALENDAR_ID primary key (ID)
);

comment on table T_BAS_EQIP_CALENDAR is
'设备工作日历';

comment on column T_BAS_EQIP_CALENDAR.ID is
'ID';

comment on column T_BAS_EQIP_CALENDAR.EQUIP_CODE is
'设备';

comment on column T_BAS_EQIP_CALENDAR.DATE_OF_WORK is
'工作日，使用字符串，形如：20120910';

comment on column T_BAS_EQIP_CALENDAR.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_EQIP_CALENDAR.CREATE_TIME is
'创建时间';

comment on column T_BAS_EQIP_CALENDAR.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_EQIP_CALENDAR.MODIFY_TIME is
'更新时间';

comment on column T_BAS_EQIP_CALENDAR.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_BAS_EQIP_CAL_SHIFT                                  */
/*==============================================================*/
create table T_BAS_EQIP_CAL_SHIFT 
(
   ID                   VARCHAR2(50)         not null,
   EQIP_CALENDAR_ID     VARCHAR2(50)         not null,
   WORK_SHIFT_ID        VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_EQIP_CAL_SHIFT_ID primary key (ID)
);

comment on table T_BAS_EQIP_CAL_SHIFT is
'设备班次';

comment on column T_BAS_EQIP_CAL_SHIFT.ID is
'ID';

comment on column T_BAS_EQIP_CAL_SHIFT.EQIP_CALENDAR_ID is
'设备工作日历ID';

comment on column T_BAS_EQIP_CAL_SHIFT.WORK_SHIFT_ID is
'班次ID';

comment on column T_BAS_EQIP_CAL_SHIFT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_EQIP_CAL_SHIFT.CREATE_TIME is
'创建时间';

comment on column T_BAS_EQIP_CAL_SHIFT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_EQIP_CAL_SHIFT.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_MES_CLIENT                                      */
/*==============================================================*/
create table T_BAS_MES_CLIENT 
(
   ID                   VARCHAR2(50)         not null,
   CLIENT_IP            VARCHAR2(20),
   CLIENT_MAC           VARCHAR2(50),
   CLIENT_NAME          VARCHAR2(200)        not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_T_BAS_MES_CLIENT primary key (ID)
);

comment on table T_BAS_MES_CLIENT is
'MES系统终端';

comment on column T_BAS_MES_CLIENT.ID is
'ID';

comment on column T_BAS_MES_CLIENT.CLIENT_IP is
'终端IP';

comment on column T_BAS_MES_CLIENT.CLIENT_MAC is
'终端MAC';

comment on column T_BAS_MES_CLIENT.CLIENT_NAME is
'终端名';

comment on column T_BAS_MES_CLIENT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_MES_CLIENT.CREATE_TIME is
'创建时间';

comment on column T_BAS_MES_CLIENT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_MES_CLIENT.MODIFY_TIME is
'更新时间';

comment on column T_BAS_MES_CLIENT.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_BAS_MES_CLIENT_MAN_EQIP                             */
/*==============================================================*/
create table T_BAS_MES_CLIENT_MAN_EQIP 
(
   ID                   VARCHAR2(50)         not null,
   MES_CLIENT_ID        VARCHAR2(50)         not null,
   EQIP_ID              VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_MES_CLIENT_MAN_EQIP_ID primary key (ID)
);

comment on table T_BAS_MES_CLIENT_MAN_EQIP is
'MES终端管理设备';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.ID is
'ID';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.MES_CLIENT_ID is
'MES终端';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.EQIP_ID is
'设备';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.CREATE_TIME is
'创建时间';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_MES_CLIENT_MAN_EQIP.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_MONTH_CALENDAR                                  */
/*==============================================================*/
create table T_BAS_MONTH_CALENDAR 
(
   ID                   VARCHAR2(50)         not null,
   WORK_MONTH           VARCHAR2(6),
   DAY1                 CHAR(1),
   DAY2                 CHAR(1),
   DAY3                 CHAR(1),
   DAY4                 CHAR(1),
   DAY5                 CHAR(1),
   DAY6                 CHAR(1),
   DAY7                 CHAR(1),
   DAY8                 CHAR(1),
   DAY9                 CHAR(1),
   DAY10                CHAR(1),
   DAY11                CHAR(1),
   DAY12                CHAR(1),
   DAY13                CHAR(1),
   DAY14                CHAR(1),
   DAY15                CHAR(1),
   DAY16                CHAR(1),
   DAY17                CHAR(1),
   DAY18                CHAR(1),
   DAY19                CHAR(1),
   DAY20                CHAR(1),
   DAY21                CHAR(1),
   DAY22                CHAR(1),
   DAY23                CHAR(1),
   DAY24                CHAR(1),
   DAY25                CHAR(1),
   DAY26                CHAR(1),
   DAY27                CHAR(1),
   DAY28                CHAR(1),
   DAY29                CHAR(1),
   DAY30                CHAR(1),
   DAY31                CHAR(1),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_BAS_MONTH_WORK_DAY_ID primary key (ID)
);

comment on table T_BAS_MONTH_CALENDAR is
'月工作日历';

comment on column T_BAS_MONTH_CALENDAR.ID is
'ID';

comment on column T_BAS_MONTH_CALENDAR.WORK_MONTH is
'月份';

comment on column T_BAS_MONTH_CALENDAR.DAY1 is
'一号';

comment on column T_BAS_MONTH_CALENDAR.DAY2 is
'二号';

comment on column T_BAS_MONTH_CALENDAR.DAY3 is
'三号';

comment on column T_BAS_MONTH_CALENDAR.DAY4 is
'四号';

comment on column T_BAS_MONTH_CALENDAR.DAY5 is
'五号';

comment on column T_BAS_MONTH_CALENDAR.DAY6 is
'六号';

comment on column T_BAS_MONTH_CALENDAR.DAY7 is
'七号';

comment on column T_BAS_MONTH_CALENDAR.DAY8 is
'八号';

comment on column T_BAS_MONTH_CALENDAR.DAY9 is
'九号';

comment on column T_BAS_MONTH_CALENDAR.DAY10 is
'十号';

comment on column T_BAS_MONTH_CALENDAR.DAY11 is
'十一号';

comment on column T_BAS_MONTH_CALENDAR.DAY12 is
'十二号';

comment on column T_BAS_MONTH_CALENDAR.DAY13 is
'十三号';

comment on column T_BAS_MONTH_CALENDAR.DAY14 is
'十四号';

comment on column T_BAS_MONTH_CALENDAR.DAY15 is
'十五号';

comment on column T_BAS_MONTH_CALENDAR.DAY16 is
'十六号';

comment on column T_BAS_MONTH_CALENDAR.DAY17 is
'十七号';

comment on column T_BAS_MONTH_CALENDAR.DAY18 is
'十八号';

comment on column T_BAS_MONTH_CALENDAR.DAY19 is
'十九号';

comment on column T_BAS_MONTH_CALENDAR.DAY20 is
'二十号';

comment on column T_BAS_MONTH_CALENDAR.DAY21 is
'二十一号';

comment on column T_BAS_MONTH_CALENDAR.DAY22 is
'二十二号';

comment on column T_BAS_MONTH_CALENDAR.DAY23 is
'二十三号';

comment on column T_BAS_MONTH_CALENDAR.DAY24 is
'二十四号';

comment on column T_BAS_MONTH_CALENDAR.DAY25 is
'二十五号';

comment on column T_BAS_MONTH_CALENDAR.DAY26 is
'二十六号';

comment on column T_BAS_MONTH_CALENDAR.DAY27 is
'二十七号';

comment on column T_BAS_MONTH_CALENDAR.DAY28 is
'二十八号';

comment on column T_BAS_MONTH_CALENDAR.DAY29 is
'二十九号';

comment on column T_BAS_MONTH_CALENDAR.DAY30 is
'三十号';

comment on column T_BAS_MONTH_CALENDAR.DAY31 is
'三十一号';

comment on column T_BAS_MONTH_CALENDAR.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_MONTH_CALENDAR.CREATE_TIME is
'创建时间';

comment on column T_BAS_MONTH_CALENDAR.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_MONTH_CALENDAR.MODIFY_TIME is
'更新时间';

comment on column T_BAS_MONTH_CALENDAR.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_BAS_ORG                                             */
/*==============================================================*/
create table T_BAS_ORG 
(
   ID                   VARCHAR2(50)         not null,
   ORG_CODE             VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   PARENT_ID            VARCHAR2(50)         not null,
   PARENT_CODE          VARCHAR2(50)         not null,
   TYPE                 VARCHAR2(50),
   STATUS               VARCHAR2(50)         not null,
   DESCRIPTION          VARCHAR2(4000),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_ORG primary key (ID)
);

comment on table T_BAS_ORG is
'机构表';

comment on column T_BAS_ORG.ID is
'机构ID';

comment on column T_BAS_ORG.ORG_CODE is
'机构编号';

comment on column T_BAS_ORG.NAME is
'机构名称';

comment on column T_BAS_ORG.PARENT_ID is
'上级机构ID';

comment on column T_BAS_ORG.PARENT_CODE is
'上级机构编号';

comment on column T_BAS_ORG.TYPE is
'机构类型';

comment on column T_BAS_ORG.STATUS is
'状态';

comment on column T_BAS_ORG.DESCRIPTION is
'描述';

comment on column T_BAS_ORG.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ORG.CREATE_TIME is
'创建时间';

comment on column T_BAS_ORG.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ORG.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_PROPERTIES                                      */
/*==============================================================*/
create table T_BAS_PROPERTIES 
(
   ID                   VARCHAR2(50)         not null,
   KEY_K                VARCHAR2(50),
   VALUE_V              VARCHAR2(500),
   DESCRIPTION          VARCHAR2(300),
   STATUS               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_PROPERTIES_ID primary key (ID)
);

comment on table T_BAS_PROPERTIES is
'系统属性';

comment on column T_BAS_PROPERTIES.ID is
'ID';

comment on column T_BAS_PROPERTIES.KEY_K is
'键';

comment on column T_BAS_PROPERTIES.VALUE_V is
'值';

comment on column T_BAS_PROPERTIES.DESCRIPTION is
'描述';

comment on column T_BAS_PROPERTIES.STATUS is
'状态';

comment on column T_BAS_PROPERTIES.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_PROPERTIES.CREATE_TIME is
'创建时间';

comment on column T_BAS_PROPERTIES.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_PROPERTIES.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_RESOURCE                                        */
/*==============================================================*/
create table T_BAS_RESOURCE 
(
   ID                   VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   PARENT_ID            VARCHAR2(50)         not null,
   URI                  VARCHAR2(400),
   TYPE                 VARCHAR2(50),
   SEQ                  NUMBER(8),
   DESCRIPTION          VARCHAR2(4000),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_RESOURCE_ID primary key (ID)
);

comment on table T_BAS_RESOURCE is
'系统资源表';

comment on column T_BAS_RESOURCE.ID is
'资源ID';

comment on column T_BAS_RESOURCE.NAME is
'资源名称';

comment on column T_BAS_RESOURCE.PARENT_ID is
'父资源ID';

comment on column T_BAS_RESOURCE.URI is
'URI';

comment on column T_BAS_RESOURCE.TYPE is
'类型';

comment on column T_BAS_RESOURCE.SEQ is
'序列';

comment on column T_BAS_RESOURCE.DESCRIPTION is
'描述';

comment on column T_BAS_RESOURCE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_RESOURCE.CREATE_TIME is
'创建时间';

comment on column T_BAS_RESOURCE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_RESOURCE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_ROLE                                            */
/*==============================================================*/
create table T_BAS_ROLE 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   ORG_CODE             VARCHAR2(50)         not null,
   DESCRIPTION          VARCHAR2(4000),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_ROLE primary key (ID)
);

comment on table T_BAS_ROLE is
'角色表';

comment on column T_BAS_ROLE.ID is
'角色ID';

comment on column T_BAS_ROLE.CODE is
'角色代码';

comment on column T_BAS_ROLE.NAME is
'角色名称';

comment on column T_BAS_ROLE.ORG_CODE is
'角色所属机构编号';

comment on column T_BAS_ROLE.DESCRIPTION is
'描述';

comment on column T_BAS_ROLE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ROLE.CREATE_TIME is
'创建时间';

comment on column T_BAS_ROLE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ROLE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_ROLE_EQIP                                       */
/*==============================================================*/
create table T_BAS_ROLE_EQIP 
(
   ID                   VARCHAR2(50)         not null,
   ROLE_ID              VARCHAR2(50)         not null,
   EQIP_INFO_ID         VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_ROLE_EQIP_ID primary key (ID)
);

comment on table T_BAS_ROLE_EQIP is
'角色负责设备';

comment on column T_BAS_ROLE_EQIP.ID is
'ID';

comment on column T_BAS_ROLE_EQIP.ROLE_ID is
'角色';

comment on column T_BAS_ROLE_EQIP.EQIP_INFO_ID is
'设备';

comment on column T_BAS_ROLE_EQIP.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ROLE_EQIP.CREATE_TIME is
'创建时间';

comment on column T_BAS_ROLE_EQIP.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ROLE_EQIP.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_ROLE_RESOURCE                                   */
/*==============================================================*/
create table T_BAS_ROLE_RESOURCE 
(
   ID                   VARCHAR2(50)         not null,
   ROLE_ID              VARCHAR2(50)         not null,
   RESOURCE_ID          VARCHAR2(50)         not null,
   ROLE_QUERY           CHAR(1)              not null,
   ROLE_CREATE          CHAR(1)              not null,
   ROLE_DELETE          CHAR(1)              not null,
   ROLE_EDIT            CHAR(1)              not null,
   ROLE_ADVANCED        CHAR(1)              not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_ROLE_RESOURCE primary key (ID)
);

comment on table T_BAS_ROLE_RESOURCE is
'角色资源权限表';

comment on column T_BAS_ROLE_RESOURCE.ID is
'RR_ID';

comment on column T_BAS_ROLE_RESOURCE.ROLE_ID is
'角色ID';

comment on column T_BAS_ROLE_RESOURCE.RESOURCE_ID is
'资源ID';

comment on column T_BAS_ROLE_RESOURCE.ROLE_QUERY is
'查询权限';

comment on column T_BAS_ROLE_RESOURCE.ROLE_CREATE is
'新增权限';

comment on column T_BAS_ROLE_RESOURCE.ROLE_DELETE is
'删除权限';

comment on column T_BAS_ROLE_RESOURCE.ROLE_EDIT is
'修改权限';

comment on column T_BAS_ROLE_RESOURCE.ROLE_ADVANCED is
'高级权限';

comment on column T_BAS_ROLE_RESOURCE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_ROLE_RESOURCE.CREATE_TIME is
'创建时间';

comment on column T_BAS_ROLE_RESOURCE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_ROLE_RESOURCE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_SYSTEM_PARAM_CONFIG                             */
/*==============================================================*/
create table T_BAS_SYSTEM_PARAM_CONFIG 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   VALUE                VARCHAR2(200)        not null,
   TYPE                 VARCHAR2(50),
   DESCRIPTION          VARCHAR2(400),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   STATUS               CHAR(1)              default '1' not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_BAS_SYS_PARA_CONF_ID primary key (ID)
);

comment on table T_BAS_SYSTEM_PARAM_CONFIG is
'系统业务参数配置';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.ID is
'参数ID';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.CODE is
'参数CODE';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.NAME is
'参数名';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.VALUE is
'参数值';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.TYPE is
'参数类型';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.DESCRIPTION is
'描述';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.CREATE_TIME is
'创建时间';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.MODIFY_TIME is
'更新时间';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.STATUS is
'数据状态';

comment on column T_BAS_SYSTEM_PARAM_CONFIG.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_BAS_SYS_MESSAGE                                     */
/*==============================================================*/
create table T_BAS_SYS_MESSAGE 
(
   ID                   VARCHAR2(50)         not null,
   MESSAGE_TITLE        VARCHAR2(300),
   MESSAGE_CONTENT      VARCHAR2(2000),
   IS_NEW               CHAR(1)              default '1',
   HASREAD              CHAR(1)              default '0' not null,
   MESSAGE_RECEIVER     VARCHAR2(50),
   RECEIVE_TIME         TIMESTAMP,
   READ_TIME            TIMESTAMP,
   ORG_CODE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_T_BAS_SYS_MESSAGE_ID primary key (ID)
);

comment on table T_BAS_SYS_MESSAGE is
'系统消息';

comment on column T_BAS_SYS_MESSAGE.ID is
'ID';

comment on column T_BAS_SYS_MESSAGE.MESSAGE_TITLE is
'消息标题';

comment on column T_BAS_SYS_MESSAGE.MESSAGE_CONTENT is
'消息内容';

comment on column T_BAS_SYS_MESSAGE.IS_NEW is
'是否新消息';

comment on column T_BAS_SYS_MESSAGE.HASREAD is
'是否阅读';

comment on column T_BAS_SYS_MESSAGE.MESSAGE_RECEIVER is
'消息接收人';

comment on column T_BAS_SYS_MESSAGE.RECEIVE_TIME is
'接收时间';

comment on column T_BAS_SYS_MESSAGE.READ_TIME is
'阅读时间';

comment on column T_BAS_SYS_MESSAGE.ORG_CODE is
'数据所属组织';

comment on column T_BAS_SYS_MESSAGE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_SYS_MESSAGE.CREATE_TIME is
'创建时间';

comment on column T_BAS_SYS_MESSAGE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_SYS_MESSAGE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_USER                                            */
/*==============================================================*/
create table T_BAS_USER 
(
   ID                   VARCHAR2(50)         not null,
   USER_CODE            VARCHAR2(50)         not null,
   PASSWORD             VARCHAR2(50)         not null,
   STATUS               VARCHAR2(50)         not null,
   HASH_USER_CODE       VARCHAR2(100),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   SUPPLEMENTARY        VARCHAR2(4000),
   constraint PK_BAS_USER primary key (ID)
);

comment on table T_BAS_USER is
'用户表';

comment on column T_BAS_USER.ID is
'用户ID';

comment on column T_BAS_USER.USER_CODE is
'员工号';

comment on column T_BAS_USER.PASSWORD is
'密码';

comment on column T_BAS_USER.STATUS is
'状态';

comment on column T_BAS_USER.HASH_USER_CODE is
'员工号加密';

comment on column T_BAS_USER.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_USER.CREATE_TIME is
'创建时间';

comment on column T_BAS_USER.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_USER.MODIFY_TIME is
'更新时间';

comment on column T_BAS_USER.SUPPLEMENTARY is
'补充信息';

/*==============================================================*/
/* Table: T_BAS_USER_ROLE                                       */
/*==============================================================*/
create table T_BAS_USER_ROLE 
(
   ID                   VARCHAR2(50)         not null,
   USER_ID              VARCHAR2(50)         not null,
   ROLE_ID              VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_BAS_USER_ROLE primary key (ID)
);

comment on table T_BAS_USER_ROLE is
'用户角色关联表';

comment on column T_BAS_USER_ROLE.ID is
'UR_ID';

comment on column T_BAS_USER_ROLE.USER_ID is
'用户ID';

comment on column T_BAS_USER_ROLE.ROLE_ID is
'角色ID';

comment on column T_BAS_USER_ROLE.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_USER_ROLE.CREATE_TIME is
'创建时间';

comment on column T_BAS_USER_ROLE.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_USER_ROLE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_BAS_WEEK_CALENDAR                                   */
/*==============================================================*/
create table T_BAS_WEEK_CALENDAR 
(
   ID                   VARCHAR2(50)         not null,
   WEEK_NO              NUMERIC(1),
   ISWORKDAY            CHAR(1)              not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_BAS_WEEK_CALENDAR_ID primary key (ID)
);

comment on table T_BAS_WEEK_CALENDAR is
'工厂周日历';

comment on column T_BAS_WEEK_CALENDAR.ID is
'ID';

comment on column T_BAS_WEEK_CALENDAR.WEEK_NO is
'星期几';

comment on column T_BAS_WEEK_CALENDAR.ISWORKDAY is
'是否工作日';

comment on column T_BAS_WEEK_CALENDAR.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_WEEK_CALENDAR.CREATE_TIME is
'创建时间';

comment on column T_BAS_WEEK_CALENDAR.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_WEEK_CALENDAR.MODIFY_TIME is
'更新时间';

comment on column T_BAS_WEEK_CALENDAR.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_BAS_WEEK_CALENDAR_SHIFT                             */
/*==============================================================*/
create table T_BAS_WEEK_CALENDAR_SHIFT 
(
   ID                   VARCHAR2(50)         not null,
   WEEK_CALENDAR_ID     VARCHAR2(50)         not null,
   WORK_SHIFT_ID        VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   STATUS               CHAR(1)              default '1' not null,
   constraint PK_BAS_WEEK_CAL_SHIFT_ID primary key (ID)
);

comment on table T_BAS_WEEK_CALENDAR_SHIFT is
'工作日班次';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.ID is
'ID';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.WEEK_CALENDAR_ID is
'周工作日历ID';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.WORK_SHIFT_ID is
'班次ID';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.CREATE_TIME is
'创建时间';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.MODIFY_TIME is
'更新时间';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.ORG_CODE is
'数据所属组织';

comment on column T_BAS_WEEK_CALENDAR_SHIFT.STATUS is
'状态';

/*==============================================================*/
/* Table: T_BAS_WORK_SHIFT                                      */
/*==============================================================*/
create table T_BAS_WORK_SHIFT 
(
   ID                   VARCHAR2(50)         not null,
   SHIFT_NAME           VARCHAR2(200)        not null,
   SHIFT_START_TIME     VARCHAR(4),
   SHIFT_END_TIME       VARCHAR(4),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   STATUS               CHAR(1)              default '1',
   constraint PK_BAS_WORK_SHIFT_ID primary key (ID)
);

comment on table T_BAS_WORK_SHIFT is
'班次信息';

comment on column T_BAS_WORK_SHIFT.ID is
'ID';

comment on column T_BAS_WORK_SHIFT.SHIFT_NAME is
'班次名称';

comment on column T_BAS_WORK_SHIFT.SHIFT_START_TIME is
'班次开始时间';

comment on column T_BAS_WORK_SHIFT.SHIFT_END_TIME is
'班次结束时间';

comment on column T_BAS_WORK_SHIFT.CREATE_USER_CODE is
'创建人';

comment on column T_BAS_WORK_SHIFT.CREATE_TIME is
'创建时间';

comment on column T_BAS_WORK_SHIFT.MODIFY_USER_CODE is
'更新人';

comment on column T_BAS_WORK_SHIFT.MODIFY_TIME is
'更新时间';

comment on column T_BAS_WORK_SHIFT.STATUS is
'状态';

/*==============================================================*/
/* Table: T_EVE_EVENT_INFO                                      */
/*==============================================================*/
create table T_EVE_EVENT_INFO 
(
   ID                   VARCHAR2(50)         not null,
   EVENT_TITLE          VARCHAR2(200),
   EVENT_CONTENT        VARCHAR2(1000),
   EVENT_TYPE_ID        VARCHAR2(50)         not null,
   EVENT_STATUS         VARCHAR2(50),
   PROCESS_TRIGGER_TIME TIMESTAMP,
   EVENT_REASON         VARCHAR2(1000),
   EVENT_RESULT         VARCHAR2(1000),
   PROCESS_ID           VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   BATCH_NO             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   ORG_CODE             VARCHAR2(50),
   EVENT_PROCESS_ID     VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   PENDING_PROCESSING   CHAR(1),
   constraint PK_EVE_EVENT_INFO_ID primary key (ID)
);

comment on table T_EVE_EVENT_INFO is
'异常事件信息';

comment on column T_EVE_EVENT_INFO.ID is
'ID';

comment on column T_EVE_EVENT_INFO.EVENT_TITLE is
'事件标题';

comment on column T_EVE_EVENT_INFO.EVENT_CONTENT is
'事件内容';

comment on column T_EVE_EVENT_INFO.EVENT_TYPE_ID is
'事件类型ID';

comment on column T_EVE_EVENT_INFO.EVENT_STATUS is
'事件状态';

comment on column T_EVE_EVENT_INFO.PROCESS_TRIGGER_TIME is
'事件升级触发时间';

comment on column T_EVE_EVENT_INFO.EVENT_REASON is
'事件原因';

comment on column T_EVE_EVENT_INFO.EVENT_RESULT is
'事件结果';

comment on column T_EVE_EVENT_INFO.PROCESS_ID is
'工序ID';

comment on column T_EVE_EVENT_INFO.EQUIP_CODE is
'设备CODE';

comment on column T_EVE_EVENT_INFO.BATCH_NO is
'批次号';

comment on column T_EVE_EVENT_INFO.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_INFO.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_INFO.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_INFO.MODIFY_TIME is
'修改时间';

comment on column T_EVE_EVENT_INFO.ORG_CODE is
'数据所属组织';

comment on column T_EVE_EVENT_INFO.EVENT_PROCESS_ID is
'事件处理流程ID';

comment on column T_EVE_EVENT_INFO.PRODUCT_CODE is
'产品代码';

comment on column T_EVE_EVENT_INFO.PENDING_PROCESSING is
'是否拦截在制品';

/*==============================================================*/
/* Table: T_EVE_EVENT_PROCESS                                   */
/*==============================================================*/
create table T_EVE_EVENT_PROCESS 
(
   ID                   VARCHAR2(50)         not null,
   EVENT_TYPE_ID        VARCHAR2(50),
   PROCESS_SEQ          NUMERIC(2),
   PROCESS_TYPE         VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   STEP_INTERVAL        NUMERIC(8,2),
   STATUS               VARCHAR2(50),
   constraint PK_EVE_EVENT_PROCESS_ID primary key (ID)
);

comment on table T_EVE_EVENT_PROCESS is
'事件处理流程定义';

comment on column T_EVE_EVENT_PROCESS.ID is
'ID';

comment on column T_EVE_EVENT_PROCESS.EVENT_TYPE_ID is
'事件类型ID';

comment on column T_EVE_EVENT_PROCESS.PROCESS_SEQ is
'事件处理步骤';

comment on column T_EVE_EVENT_PROCESS.PROCESS_TYPE is
'处理方式包括:发系统消息,发邮件';

comment on column T_EVE_EVENT_PROCESS.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_PROCESS.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_PROCESS.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_PROCESS.MODIFY_TIME is
'修改时间';

comment on column T_EVE_EVENT_PROCESS.STEP_INTERVAL is
'本步骤与上步骤间隔时间(分钟)';

comment on column T_EVE_EVENT_PROCESS.STATUS is
'状态';

/*==============================================================*/
/* Table: T_EVE_EVENT_PROCESSER                                 */
/*==============================================================*/
create table T_EVE_EVENT_PROCESSER 
(
   ID                   VARCHAR2(50)         not null,
   PROCESSER            VARCHAR2(50),
   TYPE                 VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   EVENT_PROCESS_ID     VARCHAR2(50)         not null,
   EVENT_TYPE_ID        VARCHAR2(50),
   constraint PK_EVENT_PROCESSER_ID primary key (ID)
);

comment on table T_EVE_EVENT_PROCESSER is
'事件关联处理人';

comment on column T_EVE_EVENT_PROCESSER.ID is
'ID';

comment on column T_EVE_EVENT_PROCESSER.PROCESSER is
'存放用户工号或者角色编码';

comment on column T_EVE_EVENT_PROCESSER.TYPE is
'ROLE:角色 
USER：用户';

comment on column T_EVE_EVENT_PROCESSER.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_PROCESSER.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_PROCESSER.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_PROCESSER.MODIFY_TIME is
'修改时间';

comment on column T_EVE_EVENT_PROCESSER.EVENT_PROCESS_ID is
'事件处理流程ID';

comment on column T_EVE_EVENT_PROCESSER.EVENT_TYPE_ID is
'事件类型ID';

/*==============================================================*/
/* Table: T_EVE_EVENT_PROCESS_LOG                               */
/*==============================================================*/
create table T_EVE_EVENT_PROCESS_LOG 
(
   ID                   VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   ORG_CODE             VARCHAR2(50),
   EVENT_INFO_ID        VARCHAR2(50)         not null,
   TYPE                 VARCHAR2(50),
   constraint PK_T_EVE_EVENT_PROCESS_LOG primary key (ID)
);

comment on table T_EVE_EVENT_PROCESS_LOG is
'事件处理日志';

comment on column T_EVE_EVENT_PROCESS_LOG.ID is
'ID';

comment on column T_EVE_EVENT_PROCESS_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_PROCESS_LOG.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_PROCESS_LOG.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_PROCESS_LOG.MODIFY_TIME is
'修改时间';

comment on column T_EVE_EVENT_PROCESS_LOG.ORG_CODE is
'数据所属组织';

comment on column T_EVE_EVENT_PROCESS_LOG.EVENT_INFO_ID is
'事件ID';

comment on column T_EVE_EVENT_PROCESS_LOG.TYPE is
'处理类型';

/*==============================================================*/
/* Table: T_EVE_EVENT_TYPE                                      */
/*==============================================================*/
create table T_EVE_EVENT_TYPE 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   NEED_SHOW CHAR(1),
   constraint PK_EVE_EVENT_TYPE_ID primary key (ID)
);

comment on table T_EVE_EVENT_TYPE is
'事件类型';

comment on column T_EVE_EVENT_TYPE.ID is
'ID';

comment on column T_EVE_EVENT_TYPE.CODE is
'类型代码';

comment on column T_EVE_EVENT_TYPE.NAME is
'类型名称';

comment on column T_EVE_EVENT_TYPE.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_TYPE.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_TYPE.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_TYPE.MODIFY_TIME is
'修改时间';
comment on column T_EVE_EVENT_TYPE.NEED_SHOW is
'是否在终端显示';



/*==============================================================*/
/* Table: T_EVE_EVENT_OWNER                                     */
/*==============================================================*/
create table T_EVE_EVENT_OWNER
(
  ID                   VARCHAR2(50)         not null,
  EVENT_ID             VARCHAR2(50)         not null,
  USER_CODE            VARCHAR2(50),
  CREATE_USER_CODE     VARCHAR2(50),
  CREATE_TIME          TIMESTAMP,
  MODIFY_USER_CODE     VARCHAR2(50),
  MODIFY_TIME          TIMESTAMP,
  EVENT_PROCESS_ID     VARCHAR2(50)         not null,
  constraint PK_EVE_EVENT_OWNER_ID primary key (ID)
);

comment on table T_EVE_EVENT_OWNER is
'事件责任人';

comment on column T_EVE_EVENT_OWNER.ID is
'ID';

comment on column T_EVE_EVENT_OWNER.EVENT_ID is
'事件ID';

comment on column T_EVE_EVENT_OWNER.USER_CODE is
'责任人';

comment on column T_EVE_EVENT_OWNER.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_EVENT_OWNER.CREATE_TIME is
'创建时间';

comment on column T_EVE_EVENT_OWNER.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_EVENT_OWNER.MODIFY_TIME is
'修改时间';

comment on column T_EVE_EVENT_OWNER.EVENT_PROCESS_ID is
'事件处理流程ID';


/*==============================================================*/
/* Table: T_EVE_LAST_EXECUTE_TIME_RECORD                        */
/*==============================================================*/
create table T_EVE_LAST_EXECUTE_TIME_RECORD 
(
   ID                   VARCHAR2(50)         not null,
   LAST_EXECUTE_TIME    TIMESTAMP            not null,
   MILLISEC             NUMERIC(6,2),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   TYPE                 VARCHAR2(50),
   constraint PK_T_EVE_LAST_EXECUTE_TIME_REC primary key (ID)
);

comment on table T_EVE_LAST_EXECUTE_TIME_RECORD is
'质量监控异常最后处理时间';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.ID is
'ID';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.LAST_EXECUTE_TIME is
'最后处理时间';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.MILLISEC is
'事件标签值毫秒数';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.CREATE_TIME is
'创建时间';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.MODIFY_TIME is
'修改时间';

comment on column T_EVE_LAST_EXECUTE_TIME_RECORD.TYPE is
'数据类型';

/*==============================================================*/
/* Table: T_EVE_TRIGGER_LOG                                     */
/*==============================================================*/
create table T_EVE_TRIGGER_LOG 
(
   ID                   VARCHAR2(50)         not null,
   EVENT_ID             VARCHAR2(50)         not null,
   PROCESS_ID           VARCHAR2(50),
   PROCESS_CONTENT      VARCHAR2(4000),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   ORG_CODE             VARCHAR2(50),
   constraint PK_EVE_TRIGGER_LOG_ID primary key (ID)
);

comment on table T_EVE_TRIGGER_LOG is
'事件触发日志';

comment on column T_EVE_TRIGGER_LOG.ID is
'ID';

comment on column T_EVE_TRIGGER_LOG.EVENT_ID is
'事件ID';

comment on column T_EVE_TRIGGER_LOG.PROCESS_ID is
'触发步骤ID';

comment on column T_EVE_TRIGGER_LOG.PROCESS_CONTENT is
'触发内容描述';

comment on column T_EVE_TRIGGER_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_EVE_TRIGGER_LOG.CREATE_TIME is
'创建时间';

comment on column T_EVE_TRIGGER_LOG.MODIFY_USER_CODE is
'修改人';

comment on column T_EVE_TRIGGER_LOG.MODIFY_TIME is
'修改时间';

comment on column T_EVE_TRIGGER_LOG.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_FAC_EQIP_INFO                                       */
/*==============================================================*/
create table T_FAC_EQIP_INFO 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   STATUS               VARCHAR2(50),
   TYPE                 VARCHAR2(50),
   SUB_TYPE             VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   SPECS                VARCHAR2(50),
   ENAME                VARCHAR2(200),
   BNUM                 VARCHAR2(200),
   MODEL                VARCHAR2(50),
   CENTER               VARCHAR2(200),
   USEPRICES            VARCHAR2(200),
   FACTORY              VARCHAR2(200),
   NEXT_MAINTAIN_DATE   DATE,
   NEXT_MAINTAIN_DATE_FIRST DATE,
   NEXT_MAINTAIN_DATE_SECOND DATE,
   NEXT_MAINTAIN_DATE_OVERHAUL DATE,
   MAINTAINER           VARCHAR2(100),
   MAINTAIN_DATE        NUMERIC(2)           default 1,
   MAINTAIN_DATE_FIRST  NUMERIC(2),
   MAINTAIN_DATE_SECOND NUMERIC(2),
   MAINTAIN_DATE_OVERHAUL NUMERIC(2),
   constraint PK_FAC_EQIP_INFO primary key (ID)
);

comment on table T_FAC_EQIP_INFO is
'设备信息';

comment on column T_FAC_EQIP_INFO.ID is
'ID';

comment on column T_FAC_EQIP_INFO.CODE is
'设备编码';

comment on column T_FAC_EQIP_INFO.NAME is
'设备名称';

comment on column T_FAC_EQIP_INFO.STATUS is
'设备状态';

comment on column T_FAC_EQIP_INFO.TYPE is
'设备类型';

comment on column T_FAC_EQIP_INFO.SUB_TYPE is
'设备子类型';

comment on column T_FAC_EQIP_INFO.CREATE_TIME is
'创建时间';

comment on column T_FAC_EQIP_INFO.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_EQIP_INFO.MODIFY_TIME is
'修改时间';

comment on column T_FAC_EQIP_INFO.MODIFY_USER_CODE is
'修改人';

comment on column T_FAC_EQIP_INFO.ORG_CODE is
'数据所属组织';

comment on column T_FAC_EQIP_INFO.SPECS is
'规格';

comment on column T_FAC_EQIP_INFO.ENAME is
'英文名称';

comment on column T_FAC_EQIP_INFO.BNUM is
'可加工件数';

comment on column T_FAC_EQIP_INFO.MODEL is
'设备型号';

comment on column T_FAC_EQIP_INFO.CENTER is
'工作中心';

comment on column T_FAC_EQIP_INFO.USEPRICES is
'使用费';

comment on column T_FAC_EQIP_INFO.FACTORY is
'设备生产厂';

comment on column T_FAC_EQIP_INFO.NEXT_MAINTAIN_DATE is
'下次月检维修时间';

comment on column T_FAC_EQIP_INFO.NEXT_MAINTAIN_DATE_FIRST is
'下次一级保养维修时间';

comment on column T_FAC_EQIP_INFO.NEXT_MAINTAIN_DATE_SECOND is
'下次二级保养维修时间';

comment on column T_FAC_EQIP_INFO.NEXT_MAINTAIN_DATE_OVERHAUL is
'下次大修维修时间';

comment on column T_FAC_EQIP_INFO.MAINTAINER is
'维修负责人';

comment on column T_FAC_EQIP_INFO.MAINTAIN_DATE is
'月检固定维修日';

comment on column T_FAC_EQIP_INFO.MAINTAIN_DATE_FIRST is
'一级保养固定维修日';

comment on column T_FAC_EQIP_INFO.MAINTAIN_DATE_SECOND is
'二级保养固定维修日';

comment on column T_FAC_EQIP_INFO.MAINTAIN_DATE_OVERHAUL is
'大修保养固定维修日';

/*==============================================================*/
/* Table: T_FAC_EQUIP_MAINTAIN_STATE                            */
/*==============================================================*/
create table T_FAC_EQUIP_MAINTAIN_STATE 
(
   ID                   VARCHAR2(50)         not null,
   EVENT_INFO_ID        VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50)         not null,
   EQUIP_NAME           VARCHAR2(200)        not null,
   MAINTAIN_TYPE        VARCHAR2(50)         not null,
   START_DATE           TIMESTAMP,
   LAST_MAINTAIN_DATE   TIMESTAMP,
   TRIGGER_CYCLE        NUMERIC(6,0),
   TIME_NEEDED          NUMERIC(6,2),
   COMPLETED            CHAR(1)              default '0',
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   MAINTAINER           VARCHAR2(100),
   constraint PK_T_FAC_EQUIP_MAINTAIN_STATE primary key (ID)
);

comment on table T_FAC_EQUIP_MAINTAIN_STATE is
'设备维护状态';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.ID is
'ID';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.EVENT_INFO_ID is
'EVENT_INFO_ID';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.EQUIP_CODE is
'设备编码';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.EQUIP_NAME is
'设备名称';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.MAINTAIN_TYPE is
'维护模版类型';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.START_DATE is
'维护预计开始时间';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.LAST_MAINTAIN_DATE is
'设备上次维护时间';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.TRIGGER_CYCLE is
'触发周期';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.TIME_NEEDED is
'维修用时';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.COMPLETED is
'已完成';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.CREATE_USER_CODE is
'创建人员';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.CREATE_TIME is
'创建时间';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.MODIFY_TIME is
'修改时间';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.MODIFY_USER_CODE is
'修改人';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.ORG_CODE is
'数据所属组织';

comment on column T_FAC_EQUIP_MAINTAIN_STATE.MAINTAINER is
'维修负责人';

/*==============================================================*/
/* Table: T_FAC_MAINTAIN_ITEM                                   */
/*==============================================================*/
create table T_FAC_MAINTAIN_ITEM 
(
   ID                   VARCHAR2(50)         not null,
   TEMP_ID              VARCHAR2(50),
   DESCRIBE             VARCHAR2(2000)       not null,
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   constraint PK_T_FAC_MAINTAIN_ITEM primary key (ID)
);

comment on table T_FAC_MAINTAIN_ITEM is
'设备维护项目';

comment on column T_FAC_MAINTAIN_ITEM.ID is
'ID';

comment on column T_FAC_MAINTAIN_ITEM.TEMP_ID is
'点检模版ID';

comment on column T_FAC_MAINTAIN_ITEM.DESCRIBE is
'项目说明';

comment on column T_FAC_MAINTAIN_ITEM.CREATE_TIME is
'创建时间';

comment on column T_FAC_MAINTAIN_ITEM.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_MAINTAIN_ITEM.MODIFY_TIME is
'修改时间';

comment on column T_FAC_MAINTAIN_ITEM.MODIFY_USER_CODE is
'修改人';

/*==============================================================*/
/* Table: T_FAC_MAINTAIN_RECORD                                 */
/*==============================================================*/
create table T_FAC_MAINTAIN_RECORD 
(
   ID                   VARCHAR2(50)         not null,
   TMPL_ID              VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   START_TIME           TIMESTAMP,
   FINISH_TIME          TIMESTAMP,
   TYPE                 VARCHAR2(50),
   STATUS               VARCHAR2(50),
   constraint PK_T_FAC_MAINTAIN_RECORD primary key (ID)
);

comment on table T_FAC_MAINTAIN_RECORD is
'维护记录表';

comment on column T_FAC_MAINTAIN_RECORD.ID is
'ID';

comment on column T_FAC_MAINTAIN_RECORD.TMPL_ID is
'模版ID';

comment on column T_FAC_MAINTAIN_RECORD.EQUIP_CODE is
'设备编码';

comment on column T_FAC_MAINTAIN_RECORD.CREATE_USER_CODE is
'维修人员';

comment on column T_FAC_MAINTAIN_RECORD.CREATE_TIME is
'创建时间';

comment on column T_FAC_MAINTAIN_RECORD.MODIFY_TIME is
'修改时间';

comment on column T_FAC_MAINTAIN_RECORD.MODIFY_USER_CODE is
'修改人';

comment on column T_FAC_MAINTAIN_RECORD.START_TIME is
'维修开始时间';

comment on column T_FAC_MAINTAIN_RECORD.FINISH_TIME is
'维修完成时间';

comment on column T_FAC_MAINTAIN_RECORD.TYPE is
'模版类型';

comment on column T_FAC_MAINTAIN_RECORD.STATUS is
'状态';

/*==============================================================*/
/* Table: T_FAC_MAINTAIN_RECORD_ITEM                            */
/*==============================================================*/
create table T_FAC_MAINTAIN_RECORD_ITEM 
(
   ID                   VARCHAR2(50)         not null,
   RECORD_ID            VARCHAR2(50),
   DESCRIBE             VARCHAR2(2000)       not null,
   VALUE                NUMERIC(10, 2),
   IS_PASSED            CHAR(1)              default '0',
   REMARKS              VARCHAR2(1000),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   constraint PK_T_FAC_MAINTAIN_RECORD_ITEM primary key (ID)
);

comment on table T_FAC_MAINTAIN_RECORD_ITEM is
'维护记录项';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.ID is
'ID';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.RECORD_ID is
'点检表';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.DESCRIBE is
'项目说明';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.VALUE is
'测量值';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.IS_PASSED is
'是否通过检查';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.REMARKS is
'备注';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.CREATE_TIME is
'创建时间';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.MODIFY_TIME is
'修改时间';

comment on column T_FAC_MAINTAIN_RECORD_ITEM.MODIFY_USER_CODE is
'修改人';

/*==============================================================*/
/* Table: T_FAC_MAINTAIN_TEMPLATE                               */
/*==============================================================*/
create table T_FAC_MAINTAIN_TEMPLATE 
(
   ID                   VARCHAR2(50)         not null,
   MODEL                VARCHAR2(50)         not null,
   DESCRIBE             VARCHAR2(1000),
   TYPE                 VARCHAR2(50),
   TRIGGER_TYPE         VARCHAR2(50),
   TRIGGER_CYCLE        NUMERIC(6,0),
   ORG_CODE             VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   TIME                 NUMERIC(6,2),
   constraint PK_T_FAC_MAINTAIN_TEMPLATE primary key (ID)
);

comment on table T_FAC_MAINTAIN_TEMPLATE is
'设备维护模版';

comment on column T_FAC_MAINTAIN_TEMPLATE.ID is
'ID';

comment on column T_FAC_MAINTAIN_TEMPLATE.MODEL is
'设备型号';

comment on column T_FAC_MAINTAIN_TEMPLATE.DESCRIBE is
'点检说明';

comment on column T_FAC_MAINTAIN_TEMPLATE.TYPE is
'模版类型';

comment on column T_FAC_MAINTAIN_TEMPLATE.TRIGGER_TYPE is
'触发条件类型';

comment on column T_FAC_MAINTAIN_TEMPLATE.TRIGGER_CYCLE is
'触发条件周期';

comment on column T_FAC_MAINTAIN_TEMPLATE.ORG_CODE is
'数据所属组织';

comment on column T_FAC_MAINTAIN_TEMPLATE.CREATE_TIME is
'创建时间';

comment on column T_FAC_MAINTAIN_TEMPLATE.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_MAINTAIN_TEMPLATE.MODIFY_TIME is
'修改时间';

comment on column T_FAC_MAINTAIN_TEMPLATE.MODIFY_USER_CODE is
'修改人';

comment on column T_FAC_MAINTAIN_TEMPLATE.TIME is
'维修用时';

/*==============================================================*/
/* Table: T_FAC_PRODUCT_EQIP                                    */
/*==============================================================*/
create table T_FAC_PRODUCT_EQIP 
(
   ID                   VARCHAR2(50)         not null,
   PRODUCT_LINE_ID      VARCHAR2(50)         not null,
   EQUIP_ID             VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   STATUS               VARCHAR2(50),
   IS_MAIN              CHAR(1)              default '1' not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_T_FAC_PRODUCT_EQIP primary key (ID)
);

comment on table T_FAC_PRODUCT_EQIP is
'设备与生产线关系';

comment on column T_FAC_PRODUCT_EQIP.ID is
'ID';

comment on column T_FAC_PRODUCT_EQIP.PRODUCT_LINE_ID is
'生产线ID';

comment on column T_FAC_PRODUCT_EQIP.EQUIP_ID is
'设备ID';

comment on column T_FAC_PRODUCT_EQIP.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_PRODUCT_EQIP.MODIFY_TIME is
'修改时间';

comment on column T_FAC_PRODUCT_EQIP.MODIFY_USER_CODE is
'修改人';

comment on column T_FAC_PRODUCT_EQIP.ORG_CODE is
'数据所属组织';

comment on column T_FAC_PRODUCT_EQIP.STATUS is
'状态';

comment on column T_FAC_PRODUCT_EQIP.IS_MAIN is
'是否主设备';

comment on column T_FAC_PRODUCT_EQIP.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_FAC_STATUS_HISTORY                                  */
/*==============================================================*/
create table T_FAC_STATUS_HISTORY 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_ID             VARCHAR2(50)         not null,
   STATUS               VARCHAR2(50),
   START_TIME           TIMESTAMP            not null,
   END_TIME             TIMESTAMP,
   CREATE_TIME          TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   constraint PK_FAC_STATUS_HISTORY_ID primary key (ID)
);

comment on table T_FAC_STATUS_HISTORY is
'设备状态历史';

comment on column T_FAC_STATUS_HISTORY.ID is
'ID';

comment on column T_FAC_STATUS_HISTORY.EQUIP_ID is
'设备ID';

comment on column T_FAC_STATUS_HISTORY.STATUS is
'设备状态';

comment on column T_FAC_STATUS_HISTORY.START_TIME is
'状态开始时间';

comment on column T_FAC_STATUS_HISTORY.END_TIME is
'状态结束时间';

comment on column T_FAC_STATUS_HISTORY.CREATE_TIME is
'创建时间';

comment on column T_FAC_STATUS_HISTORY.CREATE_USER_CODE is
'创建人';

comment on column T_FAC_STATUS_HISTORY.MODIFY_TIME is
'修改时间';

comment on column T_FAC_STATUS_HISTORY.MODIFY_USER_CODE is
'修改人';

/*==============================================================*/
/* Table: T_FAC_WORK_TASKS                                      */
/*==============================================================*/
create table T_FAC_WORK_TASKS 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50)         not null,
   WORK_START_TIME      TIMESTAMP            not null,
   WORK_END_TIME        TIMESTAMP            not null,
   FINISHWORK           CHAR(1)              default '0' not null,
   ORDER_ITEM_PRO_DEC_ID VARCHAR2(50)         not null,
   ORG_CODE             VARCHAR2(50),
   DESCRIPTION          VARCHAR2(4000),
   constraint PK_FAC_WORK_TASKS_ID primary key (ID)
);

comment on table T_FAC_WORK_TASKS is
'设备计划加工任务负载';

comment on column T_FAC_WORK_TASKS.ID is
'ID';

comment on column T_FAC_WORK_TASKS.EQUIP_CODE is
'设备编码';

comment on column T_FAC_WORK_TASKS.WORK_START_TIME is
'计划开始加工时间';

comment on column T_FAC_WORK_TASKS.WORK_END_TIME is
'计划结束加工时间';

comment on column T_FAC_WORK_TASKS.FINISHWORK is
'是否加工完成';

comment on column T_FAC_WORK_TASKS.ORDER_ITEM_PRO_DEC_ID is
'订单分解工序ID';

comment on column T_FAC_WORK_TASKS.ORG_CODE is
'数据所属组织';

comment on column T_FAC_WORK_TASKS.DESCRIPTION is
'描述';

/*==============================================================*/
/* Index: IDX_FAC_WORK_TASKS_START_TIME                         */
/*==============================================================*/
create index IDX_FAC_WORK_TASKS_START_TIME on T_FAC_WORK_TASKS (
   WORK_START_TIME ASC
);

/*==============================================================*/
/* Table: T_INT_EQIP_ISSUE_PARMS                                */
/*==============================================================*/
create table T_INT_EQIP_ISSUE_PARMS 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   PARM_CODE            VARCHAR2(50),
   TARGET_VALUE         VARCHAR2(200),
   UP_VALUE             VARCHAR2(200),
   DOWN_VALUE           VARCHAR2(200),
   NEED_ALARM           CHAR(1)              default '1' not null,
   WORK_ORDER_ID        VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          DATE,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          DATE,
   constraint PK_INT_EQIP_ISSUE_PARMS_ID primary key (ID)
);

comment on table T_INT_EQIP_ISSUE_PARMS is
'设备下发参数';

comment on column T_INT_EQIP_ISSUE_PARMS.ID is
'ID';

comment on column T_INT_EQIP_ISSUE_PARMS.EQUIP_CODE is
'设备代码';

comment on column T_INT_EQIP_ISSUE_PARMS.PARM_CODE is
'参数代码';

comment on column T_INT_EQIP_ISSUE_PARMS.TARGET_VALUE is
'参数目标值';

comment on column T_INT_EQIP_ISSUE_PARMS.UP_VALUE is
'参数上限';

comment on column T_INT_EQIP_ISSUE_PARMS.DOWN_VALUE is
'参数下限';

comment on column T_INT_EQIP_ISSUE_PARMS.NEED_ALARM is
'超差是否报警';

comment on column T_INT_EQIP_ISSUE_PARMS.WORK_ORDER_ID is
'生产单ID';

comment on column T_INT_EQIP_ISSUE_PARMS.CREATE_USER_CODE is
'创建人';

comment on column T_INT_EQIP_ISSUE_PARMS.CREATE_TIME is
'创建时间';

comment on column T_INT_EQIP_ISSUE_PARMS.MODIFY_USER_CODE is
'更新人';

comment on column T_INT_EQIP_ISSUE_PARMS.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_INT_EQIP_SIGN                                       */
/*==============================================================*/
create table T_INT_EQIP_SIGN 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   NEED_REFRESH         CHAR(1)              not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          DATE,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          DATE,
   constraint PK_INT_EQIP_SIGN_ID primary key (ID)
);

comment on table T_INT_EQIP_SIGN is
'设备数据下发信号';

comment on column T_INT_EQIP_SIGN.ID is
'ID';

comment on column T_INT_EQIP_SIGN.EQUIP_CODE is
'设备编码';

comment on column T_INT_EQIP_SIGN.NEED_REFRESH is
'是否更新';

comment on column T_INT_EQIP_SIGN.CREATE_USER_CODE is
'创建人';

comment on column T_INT_EQIP_SIGN.CREATE_TIME is
'创建时间';

comment on column T_INT_EQIP_SIGN.MODIFY_USER_CODE is
'更新人';

comment on column T_INT_EQIP_SIGN.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_INT_EQUIP_MES_WW_MAPPING                            */
/*==============================================================*/
create table T_INT_EQUIP_MES_WW_MAPPING 
(
   ID                   VARCHAR2(50)         not null,
   AC_EQUIP_CODE        VARCHAR2(50),
   TAG_NAME             VARCHAR2(200),
   EQUIP_CODE           VARCHAR2(50),
   PARM_CODE            VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          DATE,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          DATE,
   EVENT_TYPE           VARCHAR2(50),
   constraint PK_INT_EQUIP_MES_WW_MAPPING_ID primary key (ID)
);

comment on table T_INT_EQUIP_MES_WW_MAPPING is
'设备参数代码WW与MES映射表';

comment on column T_INT_EQUIP_MES_WW_MAPPING.ID is
'ID';

comment on column T_INT_EQUIP_MES_WW_MAPPING.AC_EQUIP_CODE is
'采集系统设备代码';

comment on column T_INT_EQUIP_MES_WW_MAPPING.TAG_NAME is
'标签名';

comment on column T_INT_EQUIP_MES_WW_MAPPING.EQUIP_CODE is
'设备代码';

comment on column T_INT_EQUIP_MES_WW_MAPPING.PARM_CODE is
'设备参数';

comment on column T_INT_EQUIP_MES_WW_MAPPING.CREATE_USER_CODE is
'创建人';

comment on column T_INT_EQUIP_MES_WW_MAPPING.CREATE_TIME is
'创建时间';

comment on column T_INT_EQUIP_MES_WW_MAPPING.MODIFY_USER_CODE is
'更新人';

comment on column T_INT_EQUIP_MES_WW_MAPPING.MODIFY_TIME is
'更新时间';

comment on column T_INT_EQUIP_MES_WW_MAPPING.EVENT_TYPE is
'报警类型';

/*==============================================================*/
/* Table: T_INT_EMAIL_MESSAGE                                   */
/*==============================================================*/
create table T_INT_EMAIL_MESSAGE 
(
   ID                   VARCHAR2(50)         not null,
   CONSIGNEE            VARCHAR2(1000),
   MES_TITLE            VARCHAR2(100),
   MES_CONTENT          VARCHAR2(1000),
   SEND_TIMES           NUMBER(2),
   STATUS               VARCHAR2(50),
   MES_TYPE             VARCHAR2(50),
   EXCEPTION_DESCRIPTION VARCHAR2(2000),
   SEND_DATE            TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_T_INT_EMAIL_MESSAGE primary key (ID)
);

comment on table T_INT_EMAIL_MESSAGE is
'邮件/短信表';

comment on column T_INT_EMAIL_MESSAGE.ID is
'唯一标识符';

comment on column T_INT_EMAIL_MESSAGE.CONSIGNEE is
'收件人id';

comment on column T_INT_EMAIL_MESSAGE.MES_TITLE is
'标题';

comment on column T_INT_EMAIL_MESSAGE.MES_CONTENT is
'内容';

comment on column T_INT_EMAIL_MESSAGE.SEND_TIMES is
'发送次数，最多三次';

comment on column T_INT_EMAIL_MESSAGE.STATUS is
'状态：‘1’新建，‘2’已处理，‘3’发送成功';

comment on column T_INT_EMAIL_MESSAGE.MES_TYPE is
'类型：‘1’邮件，‘2’短信';

comment on column T_INT_EMAIL_MESSAGE.EXCEPTION_DESCRIPTION is
'异常描述';

comment on column T_INT_EMAIL_MESSAGE.SEND_DATE is
'发送时间';

comment on column T_INT_EMAIL_MESSAGE.CREATE_USER_CODE is
'创建人';

comment on column T_INT_EMAIL_MESSAGE.CREATE_TIME is
'创建时间';

comment on column T_INT_EMAIL_MESSAGE.MODIFY_USER_CODE is
'更新人';

comment on column T_INT_EMAIL_MESSAGE.MODIFY_TIME is
'更新时间';


/*==============================================================*/
/* Table: T_INV_INVENTORY                                       */
/*==============================================================*/
create table T_INV_INVENTORY 
(
   ID                   VARCHAR2(50)         not null,
   LOCATION_ID          VARCHAR2(50),
   WAREHOUSE_ID         VARCHAR2(50)         not null,
   MATERIAL_CODE        VARCHAR2(50),
   MATERIAL_NAME        VARCHAR2(200)        not null,
   BAR_CODE             VARCHAR2(50),
   QUANTITY             NUMERIC(10,2),
   LOCKED_QUANTITY      NUMERIC(10,2),
   UNIT                 VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   constraint PK_INV_INVENTORY_ID primary key (ID)
);

comment on table T_INV_INVENTORY is
'库存';

comment on column T_INV_INVENTORY.ID is
'ID';

comment on column T_INV_INVENTORY.LOCATION_ID is
'库位ID';

comment on column T_INV_INVENTORY.WAREHOUSE_ID is
'仓库ID';

comment on column T_INV_INVENTORY.MATERIAL_CODE is
'物料代码';

comment on column T_INV_INVENTORY.MATERIAL_NAME is
'物料名称';

comment on column T_INV_INVENTORY.BAR_CODE is
'批次条码';

comment on column T_INV_INVENTORY.QUANTITY is
'数量';

comment on column T_INV_INVENTORY.LOCKED_QUANTITY is
'冻结量';

comment on column T_INV_INVENTORY.UNIT is
'数量单位';

comment on column T_INV_INVENTORY.CREATE_TIME is
'创建时间';

comment on column T_INV_INVENTORY.CREATE_USER_CODE is
'创建人';

comment on column T_INV_INVENTORY.MODIFY_TIME is
'修改时间';

comment on column T_INV_INVENTORY.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_INVENTORY.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Index: INDEX_INVENTORY_MATERIAL_CODE                         */
/*==============================================================*/
create index INDEX_INVENTORY_MATERIAL_CODE on T_INV_INVENTORY (
   MATERIAL_CODE ASC
);

/*==============================================================*/
/* Table: T_INV_INVENTORY_DETAIL                                */
/*==============================================================*/
create table T_INV_INVENTORY_DETAIL 
(
   ID                   VARCHAR2(50)         not null,
   INVENTORY_ID         VARCHAR2(50)         not null,
   LENGTH               NUMERIC(10,2),
   SEQ                  NUMERIC(4),
   STATUS               VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   constraint PK_INV_INVENTORY_DETAIL_ID primary key (ID)
);

comment on table T_INV_INVENTORY_DETAIL is
'库存明细';

comment on column T_INV_INVENTORY_DETAIL.ID is
'ID';

comment on column T_INV_INVENTORY_DETAIL.INVENTORY_ID is
'库存ID';

comment on column T_INV_INVENTORY_DETAIL.LENGTH is
'长度';

comment on column T_INV_INVENTORY_DETAIL.SEQ is
'顺序号';

comment on column T_INV_INVENTORY_DETAIL.STATUS is
'状态';

comment on column T_INV_INVENTORY_DETAIL.CREATE_TIME is
'创建时间';

comment on column T_INV_INVENTORY_DETAIL.CREATE_USER_CODE is
'创建人';

comment on column T_INV_INVENTORY_DETAIL.MODIFY_TIME is
'修改时间';

comment on column T_INV_INVENTORY_DETAIL.MODIFY_USER_CODE is
'修改人';

/*==============================================================*/
/* Table: T_INV_INVENTORY_LOG                                   */
/*==============================================================*/
create table T_INV_INVENTORY_LOG 
(
   ID                   VARCHAR2(50)         not null,
   INVENTORY_ID         VARCHAR2(50)         not null,
   QUANTITY             NUMERIC(10,2),
   TYPE                 VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   constraint PK_T_INV_INVENTORY_LOG primary key (ID)
);

comment on table T_INV_INVENTORY_LOG is
'库存交易日志';

comment on column T_INV_INVENTORY_LOG.ID is
'ID';

comment on column T_INV_INVENTORY_LOG.INVENTORY_ID is
'库存ID';

comment on column T_INV_INVENTORY_LOG.QUANTITY is
'交易量';

comment on column T_INV_INVENTORY_LOG.TYPE is
'日志类型';

comment on column T_INV_INVENTORY_LOG.CREATE_TIME is
'创建时间';

comment on column T_INV_INVENTORY_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_INV_INVENTORY_LOG.MODIFY_TIME is
'修改时间';

comment on column T_INV_INVENTORY_LOG.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_INVENTORY_LOG.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_INV_LOCATION                                        */
/*==============================================================*/
create table T_INV_LOCATION 
(
   ID                   VARCHAR2(50)         not null,
   WAREHOUSE_ID         VARCHAR2(50)         not null,
   PROCESS_CODE         VARCHAR2(50),
   LOCATION_NAME        VARCHAR2(200)        not null,
   LOCATION_X           VARCHAR2(10),
   LOCATION_Y           VARCHAR2(10),
   LOCATION_Z           VARCHAR2(10),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   TYPE                 VARCHAR2(50),
   constraint PK_INV_LOCATION_ID primary key (ID)
);

comment on table T_INV_LOCATION is
'库位';

comment on column T_INV_LOCATION.ID is
'ID';

comment on column T_INV_LOCATION.WAREHOUSE_ID is
'所在仓库';

comment on column T_INV_LOCATION.PROCESS_CODE is
'所在工序';

comment on column T_INV_LOCATION.LOCATION_NAME is
'库位';

comment on column T_INV_LOCATION.LOCATION_X is
'位置X';

comment on column T_INV_LOCATION.LOCATION_Y is
'位置Y';

comment on column T_INV_LOCATION.LOCATION_Z is
'位置Z';

comment on column T_INV_LOCATION.CREATE_TIME is
'创建时间';

comment on column T_INV_LOCATION.CREATE_USER_CODE is
'创建人';

comment on column T_INV_LOCATION.MODIFY_TIME is
'修改时间';

comment on column T_INV_LOCATION.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_LOCATION.ORG_CODE is
'数据所属组织';

comment on column T_INV_LOCATION.TYPE is
'库位类型';

/*==============================================================*/
/* Table: T_INV_MAT                                             */
/*==============================================================*/
create table T_INV_MAT 
(
   ID                   VARCHAR2(50)         not null,
   MAT_CODE             VARCHAR2(50),
   MAT_NAME             VARCHAR2(200)        not null,
   MAT_TYPE             VARCHAR2(50),
   HAS_PIC              CHAR(1)              default '0' not null,
   IS_PRODUCT           CHAR(1)              default '0' not null,
   PRODUCT_CODE         VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   REMARK               VARCHAR2(4000),
   TEMPLET_ID           VARCHAR2(50)         not null,
   ORG_CODE             VARCHAR2(50),
   COLOR                VARCHAR2(50),
   MAT_SIZE             NUMERIC(10,2),
   MAT_SPEC             VARCHAR2(200),
   constraint PK_INV_INV_MAT_ID primary key (ID)
);

comment on table T_INV_MAT is
'物料表';

comment on column T_INV_MAT.ID is
'ID';

comment on column T_INV_MAT.MAT_CODE is
'物料代码';

comment on column T_INV_MAT.MAT_NAME is
'物料名称';

comment on column T_INV_MAT.MAT_TYPE is
'物料类型';

comment on column T_INV_MAT.HAS_PIC is
'是否有附件';

comment on column T_INV_MAT.IS_PRODUCT is
'是否产品';

comment on column T_INV_MAT.PRODUCT_CODE is
'产品代码';

comment on column T_INV_MAT.CREATE_TIME is
'创建时间';

comment on column T_INV_MAT.CREATE_USER_CODE is
'创建人';

comment on column T_INV_MAT.MODIFY_TIME is
'修改时间';

comment on column T_INV_MAT.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_MAT.REMARK is
'描述';

comment on column T_INV_MAT.TEMPLET_ID is
'物料模板ID';

comment on column T_INV_MAT.ORG_CODE is
'数据所属组织';

comment on column T_INV_MAT.COLOR is
'颜色';

comment on column T_INV_MAT.MAT_SIZE is
'大小';

comment on column T_INV_MAT.MAT_SPEC is
'规格';

/*==============================================================*/
/* Table: T_INV_MAT_PROP                                        */
/*==============================================================*/
create table T_INV_MAT_PROP 
(
   ID                   VARCHAR2(50)         not null,
   MAT_ID               VARCHAR2(50),
   TEMPLET_DETAIL_ID    VARCHAR2(50)         not null,
   PROP_TARGET_VALUE    VARCHAR2(200),
   PROP_VALUE_FORMULA   VARCHAR2(200),
   PROP_MAX_VALUE       VARCHAR2(200),
   PROP_MIN_VALUE       VARCHAR2(200),
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   "DESC"               VARCHAR2(4000),
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_INV_MAT_PROP_ID primary key (ID)
);

comment on table T_INV_MAT_PROP is
'物料属性表';

comment on column T_INV_MAT_PROP.ID is
'ID';

comment on column T_INV_MAT_PROP.MAT_ID is
'物料表_ID';

comment on column T_INV_MAT_PROP.TEMPLET_DETAIL_ID is
'模板属性明细ID';

comment on column T_INV_MAT_PROP.PROP_TARGET_VALUE is
'属性值';

comment on column T_INV_MAT_PROP.PROP_VALUE_FORMULA is
'属性值计算公式';

comment on column T_INV_MAT_PROP.PROP_MAX_VALUE is
'属性最大值';

comment on column T_INV_MAT_PROP.PROP_MIN_VALUE is
'属性最小值';

comment on column T_INV_MAT_PROP.CREATE_USER_CODE is
'创建人';

comment on column T_INV_MAT_PROP.MODIFY_TIME is
'修改时间';

comment on column T_INV_MAT_PROP.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_MAT_PROP."DESC" is
'描述';

comment on column T_INV_MAT_PROP.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_INV_TEMPLET                                         */
/*==============================================================*/
create table T_INV_TEMPLET 
(
   ID                   VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   "DESC"               VARCHAR2(200),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CDOE             VARCHAR2(50),
   constraint PK_INV_TEMPLET_ID primary key (ID)
);

comment on table T_INV_TEMPLET is
'物料模板表';

comment on column T_INV_TEMPLET.ID is
'ID';

comment on column T_INV_TEMPLET.NAME is
'模板名称';

comment on column T_INV_TEMPLET."DESC" is
'模板描述';

comment on column T_INV_TEMPLET.CREATE_TIME is
'创建时间';

comment on column T_INV_TEMPLET.CREATE_USER_CODE is
'创建人';

comment on column T_INV_TEMPLET.MODIFY_TIME is
'修改时间';

comment on column T_INV_TEMPLET.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_TEMPLET.ORG_CDOE is
'数据所属组织';

/*==============================================================*/
/* Table: T_INV_TEMPLET_DETAIL                                  */
/*==============================================================*/
create table T_INV_TEMPLET_DETAIL 
(
   ID                   VARCHAR2(50)         not null,
   TEMPLET_ID           VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   PROP_NAME            VARCHAR2(200)        not null,
   PROP_CODE            VARCHAR2(50),
   PROP_TYPE            VARCHAR2(50),
   PROP_SEQ             NUMERIC(4),
   PROP_IS_RANGE        CHAR(1)              default '0' not null,
   constraint PK_INV_TEMPLET_DETAIL_ID primary key (ID)
);

comment on table T_INV_TEMPLET_DETAIL is
'物料模板属性明细表';

comment on column T_INV_TEMPLET_DETAIL.ID is
'ID';

comment on column T_INV_TEMPLET_DETAIL.TEMPLET_ID is
'模板ID';

comment on column T_INV_TEMPLET_DETAIL.CREATE_TIME is
'创建时间';

comment on column T_INV_TEMPLET_DETAIL.CREATE_USER_CODE is
'创建人';

comment on column T_INV_TEMPLET_DETAIL.MODIFY_TIME is
'修改时间';

comment on column T_INV_TEMPLET_DETAIL.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_TEMPLET_DETAIL.PROP_NAME is
'属性名称';

comment on column T_INV_TEMPLET_DETAIL.PROP_CODE is
'属性CODE';

comment on column T_INV_TEMPLET_DETAIL.PROP_TYPE is
'属性类型';

comment on column T_INV_TEMPLET_DETAIL.PROP_SEQ is
'顺序';

comment on column T_INV_TEMPLET_DETAIL.PROP_IS_RANGE is
'属性是否范围';

/*==============================================================*/
/* Table: T_INV_WAREHOUSE                                       */
/*==============================================================*/
create table T_INV_WAREHOUSE 
(
   ID                   VARCHAR2(50)         not null,
   WAREHOUSE_CODE       VARCHAR2(50),
   WAREHOUSE_NAME       VARCHAR2(200)        not null,
   ADDRESS              VARCHAR2(1000),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   TYPE                 VARCHAR2(50),
   constraint PK_INV_WAREHOUSE_ID primary key (ID)
);

comment on table T_INV_WAREHOUSE is
'仓库';

comment on column T_INV_WAREHOUSE.ID is
'ID';

comment on column T_INV_WAREHOUSE.WAREHOUSE_CODE is
'仓库代码';

comment on column T_INV_WAREHOUSE.WAREHOUSE_NAME is
'仓库名称';

comment on column T_INV_WAREHOUSE.ADDRESS is
'所在地';

comment on column T_INV_WAREHOUSE.CREATE_TIME is
'创建时间';

comment on column T_INV_WAREHOUSE.CREATE_USER_CODE is
'创建人';

comment on column T_INV_WAREHOUSE.MODIFY_TIME is
'修改时间';

comment on column T_INV_WAREHOUSE.MODIFY_USER_CODE is
'修改人';

comment on column T_INV_WAREHOUSE.ORG_CODE is
'数据所属组织';

comment on column T_INV_WAREHOUSE.TYPE is
'仓库类型';

/*==============================================================*/
/* Table: T_JOB_CONFIG                                          */
/*==============================================================*/
create table T_JOB_CONFIG 
(
   ID                   VARCHAR2(50)         not null,
   SCHEDULER_ID         VARCHAR2(50),
   SCHEDULER_WAR        VARCHAR2(200),
   NAME                 VARCHAR2(200),
   DESCRIPTION          VARCHAR2(200),
   TYPE                 VARCHAR2(50),
   ENABLE               CHAR(1)              default '1' not null,
   CRON_EXPRESS         VARCHAR2(200),
   SIMPLE_START_TIME    TIMESTAMP,
   SIMPLE_END_TIME      TIMESTAMP,
   SIMPLE_REPEAT_COUNT  NUMERIC(3),
   SIMPLE_REPEAT_INTERVEL NUMERIC(10),
   PARAM1               VARCHAR2(200),
   PARAM2               VARCHAR2(200),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   constraint PK_JOB_CONFIG_ID primary key (ID)
);

comment on table T_JOB_CONFIG is
'定时任务设置';

comment on column T_JOB_CONFIG.ID is
'ID';

comment on column T_JOB_CONFIG.SCHEDULER_ID is
'SCHEDULER_ID';

comment on column T_JOB_CONFIG.SCHEDULER_WAR is
'任务所在包';

comment on column T_JOB_CONFIG.NAME is
'任务名称';

comment on column T_JOB_CONFIG.DESCRIPTION is
'任务描述';

comment on column T_JOB_CONFIG.TYPE is
'任务类型';

comment on column T_JOB_CONFIG.ENABLE is
'是否启用';

comment on column T_JOB_CONFIG.CRON_EXPRESS is
'定时表达式';

comment on column T_JOB_CONFIG.SIMPLE_START_TIME is
'开始时间';

comment on column T_JOB_CONFIG.SIMPLE_END_TIME is
'结束时间';

comment on column T_JOB_CONFIG.SIMPLE_REPEAT_COUNT is
'重复次数';

comment on column T_JOB_CONFIG.SIMPLE_REPEAT_INTERVEL is
'重复间隔';

comment on column T_JOB_CONFIG.PARAM1 is
'参数一';

comment on column T_JOB_CONFIG.PARAM2 is
'参数二';

comment on column T_JOB_CONFIG.CREATE_USER_CODE is
'创建人';

comment on column T_JOB_CONFIG.CREATE_TIME is
'创建时间';

comment on column T_JOB_CONFIG.MODIFY_USER_CODE is
'更新人';

comment on column T_JOB_CONFIG.MODIFY_TIME is
'更新时间';

comment on column T_JOB_CONFIG.STATUS is
'数据状态';

comment on column T_JOB_CONFIG.ORG_CODE is
'数据所属组织';

/*==============================================================*/
/* Table: T_JOB_LOG                                             */
/*==============================================================*/
create table T_JOB_LOG 
(
   ID                   VARCHAR2(50)         not null,
   SCHEDULER_ID         VARCHAR2(50)         not null,
   JOB_NAME             VARCHAR2(200),
   JOB_DESC             VARCHAR2(200),
   HOST_NAME            VARCHAR2(200)        not null,
   HOST_ADDRESS         VARCHAR2(100),
   FLAG                 CHAR(1)              not null,
   PREV_START_TIME      TIMESTAMP,
   PREV_RESULT          VARCHAR2(100),
   PREV_END_TIME        TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   ERROR_MESSAGE        VARCHAR2(4000),
   constraint PK_JOB_LOG_ID primary key (ID)
);

comment on table T_JOB_LOG is
'定时任务日志';

comment on column T_JOB_LOG.ID is
'ID';

comment on column T_JOB_LOG.SCHEDULER_ID is
'SCHEDULER_ID';

comment on column T_JOB_LOG.JOB_NAME is
'定时任务名称';

comment on column T_JOB_LOG.JOB_DESC is
'定时任务描述';

comment on column T_JOB_LOG.HOST_NAME is
'主机名称';

comment on column T_JOB_LOG.HOST_ADDRESS is
'主机地址';

comment on column T_JOB_LOG.FLAG is
'标志';

comment on column T_JOB_LOG.PREV_START_TIME is
'上次开始时间';

comment on column T_JOB_LOG.PREV_RESULT is
'上次结果';

comment on column T_JOB_LOG.PREV_END_TIME is
'上次结束时间';

comment on column T_JOB_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_JOB_LOG.CREATE_TIME is
'创建时间';

comment on column T_JOB_LOG.MODIFY_USER_CODE is
'更新人';

comment on column T_JOB_LOG.MODIFY_TIME is
'更新时间';

comment on column T_JOB_LOG.STATUS is
'数据状态';

comment on column T_JOB_LOG.ORG_CODE is
'数据所属组织';

comment on column T_JOB_LOG.ERROR_MESSAGE is
'错误消息';

/*==============================================================*/
/* Table: T_ORD_OA_CHANGE_HIS                                   */
/*==============================================================*/
create table T_ORD_OA_CHANGE_HIS 
(
   ID                   VARCHAR2(50)         not null,
   PRODUCT_ORDER_ID     VARCHAR2(50),
   OLD_OA_TIME          TIMESTAMP            not null,
   NEW_OA_TIME          TIMESTAMP            not null,
   APPROVE_TIME         TIMESTAMP            not null,
   APPROVE_USER         VARCHAR2(50),
   APPROVE_REASON       VARCHAR2(400),
   constraint PK_ORD_OA_CHANGE_HIS_ID primary key (ID)
);

comment on table T_ORD_OA_CHANGE_HIS is
'订单承诺交货期变更历史';

comment on column T_ORD_OA_CHANGE_HIS.ID is
'ID';

comment on column T_ORD_OA_CHANGE_HIS.PRODUCT_ORDER_ID is
'生产单号';

comment on column T_ORD_OA_CHANGE_HIS.OLD_OA_TIME is
'原承诺交货时间';

comment on column T_ORD_OA_CHANGE_HIS.NEW_OA_TIME is
'先承诺交货时间';

comment on column T_ORD_OA_CHANGE_HIS.APPROVE_TIME is
'批准日期';

comment on column T_ORD_OA_CHANGE_HIS.APPROVE_USER is
'批准人';

comment on column T_ORD_OA_CHANGE_HIS.APPROVE_REASON is
'批准原因';

/*==============================================================*/
/* Table: T_ORD_SALES_ORDER                                     */
/*==============================================================*/
create table T_ORD_SALES_ORDER 
(
   ID                   VARCHAR2(50)         not null,
   SALES_ORDER_NO       VARCHAR2(50),
   IMPORTANCE           NUMERIC(2),
   CUSTOMER_COMPANY     VARCHAR2(200),
   CONTRACT_NO          VARCHAR2(50),
   OPERATOR             VARCHAR2(50),
   REMARKS              VARCHAR2(4000),
   STATUS               VARCHAR2(50),
   CONFIRM_DATE         TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   constraint PK_ORD_SALES_ORDER_ID primary key (ID)
);

comment on table T_ORD_SALES_ORDER is
'客户销售订单';

comment on column T_ORD_SALES_ORDER.ID is
'ID';

comment on column T_ORD_SALES_ORDER.SALES_ORDER_NO is
'客户销售订单编号';

comment on column T_ORD_SALES_ORDER.IMPORTANCE is
'客户重要程度';

comment on column T_ORD_SALES_ORDER.CUSTOMER_COMPANY is
'客户单位';

comment on column T_ORD_SALES_ORDER.CONTRACT_NO is
'合同号';

comment on column T_ORD_SALES_ORDER.OPERATOR is
'经办人';

comment on column T_ORD_SALES_ORDER.REMARKS is
'备注说明';

comment on column T_ORD_SALES_ORDER.STATUS is
'订单状态';

comment on column T_ORD_SALES_ORDER.CONFIRM_DATE is
'订单确认日期';

comment on column T_ORD_SALES_ORDER.CREATE_USER_CODE is
'创建人';

comment on column T_ORD_SALES_ORDER.MODIFY_USER_CODE is
'修改人';

comment on column T_ORD_SALES_ORDER.CREATE_TIME is
'创建时间';

comment on column T_ORD_SALES_ORDER.MODIFY_TIME is
'修改时间';

comment on column T_ORD_SALES_ORDER.ORG_CODE is
'订单所属组织';

/*==============================================================*/
/* Table: T_ORD_SALES_ORDER_ITEM                                */
/*==============================================================*/
create table T_ORD_SALES_ORDER_ITEM 
(
   ID                   VARCHAR2(50)         not null,
   SALES_ORDER_ID       VARCHAR2(50),
   CUST_PRODUCT_TYPE    VARCHAR2(200),
   CUST_PRODUCT_SPEC    VARCHAR2(200),
   PRODUCT_CODE         VARCHAR2(50),
   PRODUCT_TYPE         VARCHAR2(200),
   PRODUCT_SPEC         VARCHAR2(200),
   NUMBER_OF_WIRES      NUMERIC(10),
   SECTION              NUMERIC(10, 2),
   WIRES_STRUCTURE      VARCHAR2(10),
   WIRES_LENGTH         NUMERIC(10,2),
   STANDARD_LENGTH      NUMERIC(10,2),
   MIN_LENGTH           NUMERIC(10,2),
   CONTRACT_AMOUNT      NUMERIC(12, 2),
   ORG_CODE             VARCHAR2(50),
   SALEORDER_LENGTH     NUMERIC(10,2),
   STATUS               VARCHAR2(50),
   REMARKS              VARCHAR2(4000),
   CRAFTS_CODE          VARCHAR2(50),
   CRAFTS_VERSION       NUMERIC(12, 0),
   LENGTH_CONSTRAINTS   VARCHAR2(400),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_ORD_SALES_ORDER_ITEM_ID primary key (ID)
);

comment on table T_ORD_SALES_ORDER_ITEM is
'客户销售订单明细';

comment on column T_ORD_SALES_ORDER_ITEM.ID is
'订单明细ID';

comment on column T_ORD_SALES_ORDER_ITEM.SALES_ORDER_ID is
'客户销售订单ID';

comment on column T_ORD_SALES_ORDER_ITEM.CUST_PRODUCT_TYPE is
'客户产品型号';

comment on column T_ORD_SALES_ORDER_ITEM.CUST_PRODUCT_SPEC is
'客户产品规格';

comment on column T_ORD_SALES_ORDER_ITEM.PRODUCT_CODE is
'产品代码';

comment on column T_ORD_SALES_ORDER_ITEM.PRODUCT_TYPE is
'产品型号';

comment on column T_ORD_SALES_ORDER_ITEM.PRODUCT_SPEC is
'产品规格';

comment on column T_ORD_SALES_ORDER_ITEM.NUMBER_OF_WIRES is
'芯数';

comment on column T_ORD_SALES_ORDER_ITEM.SECTION is
'截面';

comment on column T_ORD_SALES_ORDER_ITEM.WIRES_STRUCTURE is
'线芯结构';

comment on column T_ORD_SALES_ORDER_ITEM.WIRES_LENGTH is
'线芯长度';

comment on column T_ORD_SALES_ORDER_ITEM.STANDARD_LENGTH is
'每卷标准长度';

comment on column T_ORD_SALES_ORDER_ITEM.MIN_LENGTH is
'最短长度';

comment on column T_ORD_SALES_ORDER_ITEM.CONTRACT_AMOUNT is
'合同金额';

comment on column T_ORD_SALES_ORDER_ITEM.ORG_CODE is
'制造部门';

comment on column T_ORD_SALES_ORDER_ITEM.SALEORDER_LENGTH is
'订单长度';

comment on column T_ORD_SALES_ORDER_ITEM.STATUS is
'状态';

comment on column T_ORD_SALES_ORDER_ITEM.REMARKS is
'备注说明';

comment on column T_ORD_SALES_ORDER_ITEM.CRAFTS_CODE is
'工艺代码';

comment on column T_ORD_SALES_ORDER_ITEM.CRAFTS_VERSION is
'工艺版本号';

comment on column T_ORD_SALES_ORDER_ITEM.LENGTH_CONSTRAINTS is
'长度约束';

comment on column T_ORD_SALES_ORDER_ITEM.CREATE_USER_CODE is
'创建人';

comment on column T_ORD_SALES_ORDER_ITEM.CREATE_TIME is
'创建时间';

comment on column T_ORD_SALES_ORDER_ITEM.MODIFY_USER_CODE is
'修改人';

comment on column T_ORD_SALES_ORDER_ITEM.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_CUSTOMER_ORDER                                  */
/*==============================================================*/
create table T_PLA_CUSTOMER_ORDER 
(
   ID                   VARCHAR2(50)         not null,
   CUSTOMER_ORDER_NO    VARCHAR2(50),
   CUSTOMER_OA_DATE     TIMESTAMP,
   OA_DATE              TIMESTAMP,
   REMARKS              VARCHAR2(4000),
   STATUS               VARCHAR2(50),
   FIXED_OA             CHAR(1)              default '0' not null,
   CONFIRM_DATE         TIMESTAMP,
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   SALES_ORDER_ID       VARCHAR2(50)         not null,
   SHCEDULE_ORDER       NUMERIC(8),
   PLAN_START_DATE      TIMESTAMP,
   PLAN_FINISH_DATE     TIMESTAMP,
   LAST_OA              TIMESTAMP,
   constraint PK_PLA_CUSTOMER_ORDER_ID primary key (ID)
);

comment on table T_PLA_CUSTOMER_ORDER is
'客户生产订单';

comment on column T_PLA_CUSTOMER_ORDER.ID is
'ID';

comment on column T_PLA_CUSTOMER_ORDER.CUSTOMER_ORDER_NO is
'生产单号';

comment on column T_PLA_CUSTOMER_ORDER.CUSTOMER_OA_DATE is
'客户要求交货期';

comment on column T_PLA_CUSTOMER_ORDER.OA_DATE is
'订单交货期';

comment on column T_PLA_CUSTOMER_ORDER.REMARKS is
'备注说明';

comment on column T_PLA_CUSTOMER_ORDER.STATUS is
'订单状态';

comment on column T_PLA_CUSTOMER_ORDER.FIXED_OA is
'交货期是否固定';

comment on column T_PLA_CUSTOMER_ORDER.CONFIRM_DATE is
'交货期确认日期';

comment on column T_PLA_CUSTOMER_ORDER.CREATE_TIME is
'创建时间';

comment on column T_PLA_CUSTOMER_ORDER.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_CUSTOMER_ORDER.MODIFY_TIME is
'修改时间';

comment on column T_PLA_CUSTOMER_ORDER.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_CUSTOMER_ORDER.ORG_CODE is
'订单所属组织';

comment on column T_PLA_CUSTOMER_ORDER.SALES_ORDER_ID is
'销售订单ID';

comment on column T_PLA_CUSTOMER_ORDER.SHCEDULE_ORDER is
'排程顺序';

comment on column T_PLA_CUSTOMER_ORDER.PLAN_START_DATE is
'计划开工日期';

comment on column T_PLA_CUSTOMER_ORDER.PLAN_FINISH_DATE is
'计划完成日期';

comment on column T_PLA_CUSTOMER_ORDER.LAST_OA is
'上次OA时间';

/*==============================================================*/
/* Table: T_PLA_CUSTOMER_ORDER_ITEM                             */
/*==============================================================*/
create table T_PLA_CUSTOMER_ORDER_ITEM 
(
   ID                   VARCHAR2(50)         not null,
   CUSTOMER_ORDER_ID    VARCHAR2(50),
   ORDER_LENGTH         NUMERIC(10,2),
   CONTRACT_LENGTH      NUMERIC(10,2),
   RELEASE_DATE         TIMESTAMP,
   PRODUCT_DATE         TIMESTAMP,
   STATUS               VARCHAR2(50),
   REMARKS              VARCHAR2(4000),
   SHCEDULE_ORDER       NUMERIC(8),
   SUB_OA_DATE          TIMESTAMP,
   USE_STOCK            CHAR(1)              default '0' not null,
   USED_STOCK_LENGTH    NUMERIC(10,2),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   SALES_ORDER_ITEM_ID  VARCHAR2(50)         not null,
   CRAFTS_ID            VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   PLAN_START_DATE      TIMESTAMP,
   PLAN_FINISH_DATE     TIMESTAMP,
   IS_FIRST_TIME        CHAR(1)              default '0' not null,
   LAST_OA              TIMESTAMP,
   constraint PK_PLA_CU_ORDER_ITEM_ID primary key (ID)
);

comment on table T_PLA_CUSTOMER_ORDER_ITEM is
'客户生产订单明细';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.ID is
'订单明细ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.CUSTOMER_ORDER_ID is
'客户生产订单ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.ORDER_LENGTH is
'计划长度';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.CONTRACT_LENGTH is
'合同长度';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.RELEASE_DATE is
'下达日期';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.PRODUCT_DATE is
'投产日期';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.STATUS is
'状态';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.REMARKS is
'备注说明';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.SHCEDULE_ORDER is
'排程顺序';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.SUB_OA_DATE is
'订单明细OA';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.USE_STOCK is
'是否使用库存';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.USED_STOCK_LENGTH is
'成品库存使用总量';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.CREATE_TIME is
'创建时间';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.MODIFY_TIME is
'修改时间';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.SALES_ORDER_ITEM_ID is
'客户销售订单明细ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.CRAFTS_ID is
'工艺ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.PRODUCT_CODE is
'产品编码';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.PLAN_START_DATE is
'计划开工日期';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.PLAN_FINISH_DATE is
'计划完成日期';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.IS_FIRST_TIME is
'是否第一次生产';

comment on column T_PLA_CUSTOMER_ORDER_ITEM.LAST_OA is
'上次OA时间';

/*==============================================================*/
/* Table: T_PLA_CUSTOMER_ORDER_ITEM_DEC                         */
/*==============================================================*/
create table T_PLA_CUSTOMER_ORDER_ITEM_DEC 
(
   ID                   VARCHAR2(50)         not null,
   ORDER_ITEM_ID        VARCHAR2(50)         not null,
   LENGTH               NUMERIC(10,2),
   USE_STOCK            CHAR(1)              default '0' not null,
   STATUS               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_CU_OR_ITEM_DSC_ID primary key (ID)
);

comment on table T_PLA_CUSTOMER_ORDER_ITEM_DEC is
'将订单明细分解成卷，每卷定义长度，有利于平衡产能，加快生产';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.ID is
'ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.ORDER_ITEM_ID is
'客户订单明细ID';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.LENGTH is
'长度';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.USE_STOCK is
'是否库存冲抵';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.STATUS is
'状态';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.CREATE_TIME is
'创建时间';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_CUSTOMER_ORDER_ITEM_DEC.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_CU_ORDER_ITEM_PRO_DEC                           */
/*==============================================================*/
create table T_PLA_CU_ORDER_ITEM_PRO_DEC 
(
   ID                   VARCHAR2(50)         not null,
   ORDER_ITEM_DEC_ID    VARCHAR2(50)         not null,
   CRAFTS_ID            VARCHAR2(50),
   PROCESS_PATH         VARCHAR2(4000),
   PROCESS_NAME         VARCHAR2(200),
   HALF_PRODUCT_CODE    VARCHAR2(50),
   CONTRACT_NO          VARCHAR2(50),
   PROCESS_CODE         VARCHAR2(50),
   PROCESS_ID           VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   PLAN_WORK_HOURS      NUMERIC(6,2),
   USE_STOCK            CHAR(1)              default '0' not null,
   USED_STOCK_LENGTH    NUMERIC(10,2),
   PRODUCT_SPEC         VARCHAR2(200),
   PRODUCT_CODE         VARCHAR2(50),
   EARLIEST_START_DATE  TIMESTAMP,
   EARLIEST_FINISH_DATE TIMESTAMP,
   LATEST_START_DATE    TIMESTAMP,
   LATEST_FINISH_DATE   TIMESTAMP,
   UN_FINISHED_LENGTH   NUMERIC(10,2),
   FINISHED_LENGTH      NUMERIC(10,2),
   STATUS               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   OPTIONAL_EQUIP_CODE  VARCHAR2(500),
   IS_LOCKED            CHAR(1)              default '0',
   NEXT_ORDER_ID        VARCHAR2(50)         not null,
   FIXED_EQUIP_CODE     VARCHAR2(500),
   constraint PK_PLA_CU_OR_ITEM_PRO_DEC_ID primary key (ID)
);

comment on table T_PLA_CU_ORDER_ITEM_PRO_DEC is
'用作计算OA的时候使用，不用每次都分解改数据，只在工艺变化或者是新订单的时候分解该数据';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.ID is
'ID';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.ORDER_ITEM_DEC_ID is
'订单明细分解ID';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.CRAFTS_ID is
'工艺ID';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PROCESS_PATH is
'工序路径';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PROCESS_NAME is
'工序名称';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.HALF_PRODUCT_CODE is
'半成品代码';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.CONTRACT_NO is
'合同号';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PROCESS_CODE is
'工序CODE';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PROCESS_ID is
'工序ID';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.EQUIP_CODE is
'加工设备';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PLAN_WORK_HOURS is
'计划用时';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.USE_STOCK is
'是否使用半成品库存';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.USED_STOCK_LENGTH is
'库存使用量';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PRODUCT_SPEC is
'产品规格';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.PRODUCT_CODE is
'产品编码';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.EARLIEST_START_DATE is
'工序最早开始时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.EARLIEST_FINISH_DATE is
'工序最早结束时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.LATEST_START_DATE is
'工序最晚开始时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.LATEST_FINISH_DATE is
'工序最晚结束时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.UN_FINISHED_LENGTH is
'未生产量';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.FINISHED_LENGTH is
'已生产量';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.STATUS is
'状态';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.CREATE_TIME is
'创建时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.MODIFY_TIME is
'修改时间';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.ORG_CODE is
'数据所属组织';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.OPTIONAL_EQUIP_CODE is
'可选设备';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.IS_LOCKED is
'是否锁定';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.NEXT_ORDER_ID is
'下道工序的工单id';

comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC.FIXED_EQUIP_CODE is
'固定加工设备';

/*==============================================================*/
/* Table: T_PLA_HIGH_PRIORITY_ORDER_ITEM                        */
/*==============================================================*/
create table T_PLA_HIGH_PRIORITY_ORDER_ITEM 
(
   ID                   VARCHAR2(50)         not null,
   SEQ                  NUMERIC(8),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_HIGH_PRI_OR_ITEM_ID primary key (ID)
);

comment on table T_PLA_HIGH_PRIORITY_ORDER_ITEM is
'优先处理订单';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.ID is
'订单ID';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.SEQ is
'顺序号';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.CREATE_TIME is
'创建时间';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_HIGH_PRIORITY_ORDER_ITEM.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_HIGH_PRIORITY_PRO_DEC                           */
/*==============================================================*/
create table T_PLA_HIGH_PRIORITY_PRO_DEC 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   SEQ                  INT,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_T_PLA_HIGH_PRIORITY_PRO_DEC primary key (ID)
);

comment on table T_PLA_HIGH_PRIORITY_PRO_DEC is
'生产单优先处理订单';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.ID is
'工序用时分解ID';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.EQUIP_CODE is
'设备';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.SEQ is
'顺序号';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.CREATE_TIME is
'创建时间';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_HIGH_PRIORITY_PRO_DEC.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_INV_OA_USE_LOG                                  */
/*==============================================================*/
create table T_PLA_INV_OA_USE_LOG 
(
   ID                   VARCHAR2(50)         not null,
   MAT_CODE             VARCHAR2(50),
   INVENTORY_DETAIL_ID  VARCHAR2(50)         not null,
   MAT_BATCH_NO         VARCHAR2(50),
   USED_STOCK_LENGTH    NUMERIC(12, 2),
   STATUS               VARCHAR2(50),
   IS_PRODUCT           CHAR(1)              default '0' not null,
   REF_ID               VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_INV_OA_USE_LOG_ID primary key (ID)
);

comment on table T_PLA_INV_OA_USE_LOG is
'库存冲抵日志';

comment on column T_PLA_INV_OA_USE_LOG.ID is
'ID';

comment on column T_PLA_INV_OA_USE_LOG.MAT_CODE is
'物料CODE';

comment on column T_PLA_INV_OA_USE_LOG.INVENTORY_DETAIL_ID is
'库存明细ID';

comment on column T_PLA_INV_OA_USE_LOG.MAT_BATCH_NO is
'材料批次号';

comment on column T_PLA_INV_OA_USE_LOG.USED_STOCK_LENGTH is
'使用量';

comment on column T_PLA_INV_OA_USE_LOG.STATUS is
'状态';

comment on column T_PLA_INV_OA_USE_LOG.IS_PRODUCT is
'是否成品';

comment on column T_PLA_INV_OA_USE_LOG.REF_ID is
'参考记录ID';

comment on column T_PLA_INV_OA_USE_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_INV_OA_USE_LOG.CREATE_TIME is
'创建时间';

comment on column T_PLA_INV_OA_USE_LOG.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_INV_OA_USE_LOG.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_MRP                                             */
/*==============================================================*/
create table T_PLA_MRP 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_ID        VARCHAR2(50),
   MAT_CODE             VARCHAR2(50),
   QUANTITY             NUMERIC(10,2),
   PLAN_DATE            TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   PROCESS_CODE         VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   UNIT                 VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_MRP_ID primary key (ID)
);

comment on table T_PLA_MRP is
'物料需求清单';

comment on column T_PLA_MRP.ID is
'ID';

comment on column T_PLA_MRP.WORK_ORDER_ID is
'生产单ID';

comment on column T_PLA_MRP.MAT_CODE is
'物料代码';

comment on column T_PLA_MRP.QUANTITY is
'数量';

comment on column T_PLA_MRP.PLAN_DATE is
'需求日期';

comment on column T_PLA_MRP.STATUS is
'状态';

comment on column T_PLA_MRP.PROCESS_CODE is
'工序';

comment on column T_PLA_MRP.EQUIP_CODE is
'设备编码';

comment on column T_PLA_MRP.ORG_CODE is
'数据所属组织';

comment on column T_PLA_MRP.UNIT is
'单位';

comment on column T_PLA_MRP.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_MRP.CREATE_TIME is
'创建时间';

comment on column T_PLA_MRP.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_MRP.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_MRP_OA                                          */
/*==============================================================*/
create table T_PLA_MRP_OA 
(
   ID                   VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   ORDER_ITEM_ID        VARCHAR2(50)         not null,
   MAT_CODE             VARCHAR2(50),
   QUANTITY             NUMERIC(10,2),
   PLAN_DATE            TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   PROCESS_CODE         VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   UNIT                 VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   PRODUCT_CODE         VARCHAR2(50),
   constraint PK_PLA_MRP_OA_ID primary key (ID)
);

comment on table T_PLA_MRP_OA is
'OA物料需求清单';

comment on column T_PLA_MRP_OA.ID is
'ID';

comment on column T_PLA_MRP_OA.CONTRACT_NO is
'合同号';

comment on column T_PLA_MRP_OA.ORDER_ITEM_ID is
'客户订单明细ID';

comment on column T_PLA_MRP_OA.MAT_CODE is
'物料代码';

comment on column T_PLA_MRP_OA.QUANTITY is
'数量';

comment on column T_PLA_MRP_OA.PLAN_DATE is
'需求日期';

comment on column T_PLA_MRP_OA.STATUS is
'状态';

comment on column T_PLA_MRP_OA.PROCESS_CODE is
'工序';

comment on column T_PLA_MRP_OA.EQUIP_CODE is
'设备编码';

comment on column T_PLA_MRP_OA.ORG_CODE is
'数据所属组织';

comment on column T_PLA_MRP_OA.UNIT is
'单位';

comment on column T_PLA_MRP_OA.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_MRP_OA.CREATE_TIME is
'创建时间';

comment on column T_PLA_MRP_OA.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_MRP_OA.MODIFY_TIME is
'修改时间';

comment on column T_PLA_MRP_OA.PRODUCT_CODE is
'产品代码';

/*==============================================================*/
/* Table: T_PLA_ORDER_TASK                                      */
/*==============================================================*/
create table T_PLA_ORDER_TASK 
(
   ID                   VARCHAR2(50)         not null,
   ORDER_ITEM_PRO_DEC_ID VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   PROCESS_ID           VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   STATUS               VARCHAR2(50),
   PROCESS_PATH         VARCHAR2(4000),
   PLAN_START_DATE      TIMESTAMP            not null,
   PLAN_FINISH_DATE     TIMESTAMP            not null,
   OPERATOR             VARCHAR2(50),
   IS_LOCKED            CHAR(1)              default '0' not null,
   IS_DELAYED           CHAR(1)              default '0' not null,
   WORK_ORDER_NO        VARCHAR2(50),
   SHIFT                VARCHAR2(50),
   TASK_LENGTH          NUMERIC(10,2),
   ORG_CODE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_ORDER_TASK_ID primary key (ID)
);

comment on table T_PLA_ORDER_TASK is
'订单任务';

comment on column T_PLA_ORDER_TASK.ID is
'ID';

comment on column T_PLA_ORDER_TASK.ORDER_ITEM_PRO_DEC_ID is
'订单分解工序ID';

comment on column T_PLA_ORDER_TASK.CONTRACT_NO is
'合同号';

comment on column T_PLA_ORDER_TASK.PRODUCT_CODE is
'产品代码';

comment on column T_PLA_ORDER_TASK.PROCESS_ID is
'工序';

comment on column T_PLA_ORDER_TASK.EQUIP_CODE is
'设备';

comment on column T_PLA_ORDER_TASK.STATUS is
'状态';

comment on column T_PLA_ORDER_TASK.PROCESS_PATH is
'工序路径';

comment on column T_PLA_ORDER_TASK.PLAN_START_DATE is
'计划开工日期';

comment on column T_PLA_ORDER_TASK.PLAN_FINISH_DATE is
'计划完工日期';

comment on column T_PLA_ORDER_TASK.OPERATOR is
'经办人';

comment on column T_PLA_ORDER_TASK.IS_LOCKED is
'是否锁定';

comment on column T_PLA_ORDER_TASK.IS_DELAYED is
'是否延迟';

comment on column T_PLA_ORDER_TASK.WORK_ORDER_NO is
'生产单号';

comment on column T_PLA_ORDER_TASK.SHIFT is
'班次';

comment on column T_PLA_ORDER_TASK.TASK_LENGTH is
'任务长度';

comment on column T_PLA_ORDER_TASK.ORG_CODE is
'数据所属组织';

comment on column T_PLA_ORDER_TASK.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_ORDER_TASK.CREATE_TIME is
'创建时间';

comment on column T_PLA_ORDER_TASK.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_ORDER_TASK.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Index: INDEX_ORDER_TASK_PRO_DEC_ID                           */
/*==============================================================*/
create index INDEX_ORDER_TASK_PRO_DEC_ID on T_PLA_ORDER_TASK (
   ORDER_ITEM_PRO_DEC_ID ASC
);

/*==============================================================*/
/* Index: IDX_PLA_ORD_TASK_PLANSTARTDATE                        */
/*==============================================================*/
create index IDX_PLA_ORD_TASK_PLANSTARTDATE on T_PLA_ORDER_TASK (
   PLAN_START_DATE ASC
);

/*==============================================================*/
/* Table: T_PLA_PRODUCT                                         */
/*==============================================================*/
create table T_PLA_PRODUCT 
(
   ID                   VARCHAR2(50)         not null,
   PRODUCT_CODE         VARCHAR2(50),
   PRODUCT_NAME         VARCHAR2(200)        not null,
   PRODUCT_TYPE         VARCHAR2(200),
   PRODUCT_SPEC         VARCHAR2(200),
   STANDARD_LENGTH      NUMERIC(10,2),
   USED_STOCK           CHAR(1)              default '1' not null,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   CRAFTS_CODE          VARCHAR2(50),
   CRAFTS_VERSION       NUMERIC(12, 0),
   ORG_CODE             VARCHAR2(50),
   COMPLEX              CHAR(1)              default '0' not null,
   constraint PK_PLA_PRODUCT_ID primary key (ID)
);

comment on table T_PLA_PRODUCT is
'产品基本信息';

comment on column T_PLA_PRODUCT.ID is
'ID';

comment on column T_PLA_PRODUCT.PRODUCT_CODE is
'产品代码';

comment on column T_PLA_PRODUCT.PRODUCT_NAME is
'产品名称';

comment on column T_PLA_PRODUCT.PRODUCT_TYPE is
'产品型号';

comment on column T_PLA_PRODUCT.PRODUCT_SPEC is
'产品规格';

comment on column T_PLA_PRODUCT.STANDARD_LENGTH is
'每卷标准长度';

comment on column T_PLA_PRODUCT.USED_STOCK is
'生产过程是否允许使用库存冲抵成品和半成品';

comment on column T_PLA_PRODUCT.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_PRODUCT.CREATE_TIME is
'创建时间';

comment on column T_PLA_PRODUCT.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_PRODUCT.MODIFY_TIME is
'修改时间';

comment on column T_PLA_PRODUCT.CRAFTS_CODE is
'默认产品工艺代码';

comment on column T_PLA_PRODUCT.CRAFTS_VERSION is
'工艺版本号';

comment on column T_PLA_PRODUCT.ORG_CODE is
'数据所属组织';

comment on column T_PLA_PRODUCT.COMPLEX is
'是否复杂产品';

/*==============================================================*/
/* Table: T_PLA_PRODUCT_SOP                                     */
/*==============================================================*/
create table T_PLA_PRODUCT_SOP 
(
   ID                   VARCHAR2(50)         not null,
   PRODUCT_CODE         VARCHAR2(50),
   EARLIEST_FINISH_DATE TIMESTAMP            not null,
   LAST_FINISH_DATE     TIMESTAMP            not null,
   CREATE_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_PRODUCT_SOP_ID primary key (ID)
);

comment on table T_PLA_PRODUCT_SOP is
'产品SOP估算';

comment on column T_PLA_PRODUCT_SOP.ID is
'ID';

comment on column T_PLA_PRODUCT_SOP.PRODUCT_CODE is
'产品代码';

comment on column T_PLA_PRODUCT_SOP.EARLIEST_FINISH_DATE is
'最早完成时间';

comment on column T_PLA_PRODUCT_SOP.LAST_FINISH_DATE is
'最晚完成时间';

comment on column T_PLA_PRODUCT_SOP.CREATE_TIME is
'创建时间';

comment on column T_PLA_PRODUCT_SOP.ORG_CODE is
'所属组织';

comment on column T_PLA_PRODUCT_SOP.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_PRODUCT_SOP.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_PRODUCT_SOP.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PLA_TOOLES_RP                                       */
/*==============================================================*/
create table T_PLA_TOOLES_RP 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_ID        VARCHAR2(50),
   TOOLES               VARCHAR2(50),
   QUANYITY             NUMERIC(10),
   PLAN_DATE            TIMESTAMP            not null,
   STATUS               VARCHAR2(50),
   PROCESS_CODE         VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PLA_TOOLES_RP_ID primary key (ID)
);

comment on table T_PLA_TOOLES_RP is
'工装夹具需求清单';

comment on column T_PLA_TOOLES_RP.ID is
'ID';

comment on column T_PLA_TOOLES_RP.WORK_ORDER_ID is
'用户ID';

comment on column T_PLA_TOOLES_RP.TOOLES is
'工具代码';

comment on column T_PLA_TOOLES_RP.QUANYITY is
'数量';

comment on column T_PLA_TOOLES_RP.PLAN_DATE is
'需求日期';

comment on column T_PLA_TOOLES_RP.STATUS is
'状态';

comment on column T_PLA_TOOLES_RP.PROCESS_CODE is
'工序';

comment on column T_PLA_TOOLES_RP.ORG_CODE is
'数据所属组织';

comment on column T_PLA_TOOLES_RP.CREATE_USER_CODE is
'创建人';

comment on column T_PLA_TOOLES_RP.CREATE_TIME is
'创建时间';

comment on column T_PLA_TOOLES_RP.MODIFY_USER_CODE is
'修改人';

comment on column T_PLA_TOOLES_RP.MODIFY_TIME is
'修改时间';

/*==============================================================*/
/* Table: T_PRO_EQIP_LIST                                       */
/*==============================================================*/
create table T_PRO_EQIP_LIST 
(
   ID                   VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(50),
   PROCESS_ID           VARCHAR2(50)         not null,
   TYPE                 VARCHAR2(50),
   EQUIP_CAPACITY       NUMERIC(12,2),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   SET_UP_TIME          NUMERIC(6,0),
   SHUT_DOWN_TIME       NUMERIC(6,0),
   IS_DEFAULT           CHAR(1)              default '0' not null,
   FXPJ NUMBER(12,2),
   SXPJ NUMBER(12,2),
   FXZDZPL NUMBER(12,2),
   SXZDZPL NUMBER(12,2),   
   constraint PK_PRO_EQIP_LIST_ID primary key (ID)
);

comment on table T_PRO_EQIP_LIST is
'工序使用设备清单';

comment on column T_PRO_EQIP_LIST.ID is
'ID';

comment on column T_PRO_EQIP_LIST.EQUIP_CODE is
'辅助设备CODE或者生产线';

comment on column T_PRO_EQIP_LIST.PROCESS_ID is
'流程ID';

comment on column T_PRO_EQIP_LIST.TYPE is
'设备类型';

comment on column T_PRO_EQIP_LIST.EQUIP_CAPACITY is
'设备能力';

comment on column T_PRO_EQIP_LIST.CREATE_TIME is
'创建时间';

comment on column T_PRO_EQIP_LIST.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_EQIP_LIST.MODIFY_TIME is
'修改时间';

comment on column T_PRO_EQIP_LIST.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_EQIP_LIST.SET_UP_TIME is
'前置时间';

comment on column T_PRO_EQIP_LIST.SHUT_DOWN_TIME is
'后置时间';

comment on column T_PRO_EQIP_LIST.IS_DEFAULT is
'是否可选设备';

/*==============================================================*/
/* Table: T_PRO_PROCESS_INFO                                    */
/*==============================================================*/
create table T_PRO_PROCESS_INFO 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   SECTION              VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   SECTION_SEQ          NUMERIC(4),
   constraint PK_PRO_PROCESS_INFO_ID primary key (ID)
);

comment on table T_PRO_PROCESS_INFO is
'工序定义基本信息';

comment on column T_PRO_PROCESS_INFO.ID is
'ID';

comment on column T_PRO_PROCESS_INFO.CODE is
'工序代码';

comment on column T_PRO_PROCESS_INFO.NAME is
'工序名称';

comment on column T_PRO_PROCESS_INFO.SECTION is
'所属工段';

comment on column T_PRO_PROCESS_INFO.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_INFO.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_INFO.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PROCESS_INFO.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PROCESS_INFO.ORG_CODE is
'数据所属组织';

comment on column T_PRO_PROCESS_INFO.SECTION_SEQ is
'工段顺序';

/*==============================================================*/
/* Table: T_PRO_PROCESS_IN_OUT                                  */
/*==============================================================*/
create table T_PRO_PROCESS_IN_OUT 
(
   ID                   VARCHAR2(50)         not null,
   PRODUCT_PROCESS_ID   VARCHAR2(50)         not null,
   MAT_CODE             VARCHAR2(50),
   IN_OR_OUT            VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   QUANTITY             NUMERIC(10,5),
   QUANTITY_FORMULA     VARCHAR2(200),
   UNIT                 VARCHAR2(50),
   USE_METHOD           VARCHAR2(4000),
   constraint PK_PRO_PROCESS_IN_OUT primary key (ID)
);

comment on table T_PRO_PROCESS_IN_OUT is
'流程投入产出';

comment on column T_PRO_PROCESS_IN_OUT.ID is
'ID';

comment on column T_PRO_PROCESS_IN_OUT.PRODUCT_PROCESS_ID is
'工艺流程ID';

comment on column T_PRO_PROCESS_IN_OUT.MAT_CODE is
'物料代码';

comment on column T_PRO_PROCESS_IN_OUT.IN_OR_OUT is
'投入还是产出';

comment on column T_PRO_PROCESS_IN_OUT.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_IN_OUT.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_IN_OUT.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PROCESS_IN_OUT.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PROCESS_IN_OUT.QUANTITY is
'单位投入用量';

comment on column T_PRO_PROCESS_IN_OUT.QUANTITY_FORMULA is
'单位投入用量计算公式';

comment on column T_PRO_PROCESS_IN_OUT.UNIT is
'用量单位';

comment on column T_PRO_PROCESS_IN_OUT.USE_METHOD is
'用法';

/*==============================================================*/
/* Table: T_PRO_PROCESS_QC                                      */
/*==============================================================*/
create table T_PRO_PROCESS_QC 
(
   ID                   VARCHAR2(50)         not null,
   PROCESS_ID           VARCHAR2(50)         not null,
   CHECK_ITEM_CODE      VARCHAR2(50),
   CHECK_ITEM_NAME      VARCHAR2(200)        not null,
   FREQUENCE            NUMERIC(6,2),
   NEED_DA              CHAR(1)              default '0' not null,
   NEED_IS              CHAR(1),
   ITEM_TARGET_VALUE    VARCHAR2(200),
   ITEM_MAX_VALUE       VARCHAR2(200),
   ITEM_MIN_VALUE       VARCHAR2(200),
   DATA_TYPE            VARCHAR2(50),
   DATA_UNIT            VARCHAR2(50),
   DATA_STATUS          VARCHAR2(50),
   MARKS                VARCHAR2(4000),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   HAS_PIC              CHAR(1)              default '0' not null,
   NEED_SHOW            CHAR(1)              default '0' not null,
   NEED_FIRST_CHECK     CHAR(1)              default '0' not null,
   NEED_MIDDLE_CHECK    CHAR(1)              default '0' not null,
   NEED_IN_CHECK        CHAR(1)              default '0' not null,
   NEED_OUT_CHECK       CHAR(1)              default '0' not null,
   NEED_ALARM           CHAR(1)              default '1' not null,
   VALUE_DOMAIN         VARCHAR2(500),
   constraint PK_PRO_PROCESS_QC_ID primary key (ID)
);

comment on table T_PRO_PROCESS_QC is
'产品质量检测参数(QA)';

comment on column T_PRO_PROCESS_QC.ID is
'ID';

comment on column T_PRO_PROCESS_QC.PROCESS_ID is
'加工工序';

comment on column T_PRO_PROCESS_QC.CHECK_ITEM_CODE is
'检测项CODE';

comment on column T_PRO_PROCESS_QC.CHECK_ITEM_NAME is
'检测项名称';

comment on column T_PRO_PROCESS_QC.FREQUENCE is
'检测频率';

comment on column T_PRO_PROCESS_QC.NEED_DA is
'是否需要数采';

comment on column T_PRO_PROCESS_QC.NEED_IS is
'是否需要下发';

comment on column T_PRO_PROCESS_QC.ITEM_TARGET_VALUE is
'参数目标值';

comment on column T_PRO_PROCESS_QC.ITEM_MAX_VALUE is
'参数上限';

comment on column T_PRO_PROCESS_QC.ITEM_MIN_VALUE is
'参数下限';

comment on column T_PRO_PROCESS_QC.DATA_TYPE is
'参数数据类型';

comment on column T_PRO_PROCESS_QC.DATA_UNIT is
'参数单位';

comment on column T_PRO_PROCESS_QC.DATA_STATUS is
'数据状态';

comment on column T_PRO_PROCESS_QC.MARKS is
'备注';

comment on column T_PRO_PROCESS_QC.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_QC.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_QC.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PROCESS_QC.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PROCESS_QC.HAS_PIC is
'是否有附件';

comment on column T_PRO_PROCESS_QC.NEED_SHOW is
'是否需要在终端显示';

comment on column T_PRO_PROCESS_QC.NEED_FIRST_CHECK is
'是否要首检';

comment on column T_PRO_PROCESS_QC.NEED_MIDDLE_CHECK is
'是否要中检';

comment on column T_PRO_PROCESS_QC.NEED_IN_CHECK is
'是否要上车检';

comment on column T_PRO_PROCESS_QC.NEED_OUT_CHECK is
'是否要下车检';

comment on column T_PRO_PROCESS_QC.NEED_ALARM is
'超差是否报警';

comment on column T_PRO_PROCESS_QC.VALUE_DOMAIN is
'值域';

/*==============================================================*/
/* Table: T_PRO_PROCESS_QC_EQIP                                 */
/*==============================================================*/
create table T_PRO_PROCESS_QC_EQIP 
(
   ID                   VARCHAR2(50)         not null,
   QC_ID                VARCHAR2(50)         not null,
   EQUIP_ID             VARCHAR2(50)         not null,
   EQUIP_CODE           VARCHAR2(200),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   AC_EQUIP_CODE        VARCHAR2(50),
   constraint PK_T_PRO_PROCESS_QC_EQIP primary key (ID)
);

comment on table T_PRO_PROCESS_QC_EQIP is
'质量参数设备关联表';

comment on column T_PRO_PROCESS_QC_EQIP.ID is
'ID';

comment on column T_PRO_PROCESS_QC_EQIP.QC_ID is
'质量参数ID';

comment on column T_PRO_PROCESS_QC_EQIP.EQUIP_ID is
'设备ID';

comment on column T_PRO_PROCESS_QC_EQIP.EQUIP_CODE is
'设备CODE';

comment on column T_PRO_PROCESS_QC_EQIP.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_QC_EQIP.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_QC_EQIP.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PROCESS_QC_EQIP.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PROCESS_QC_EQIP.AC_EQUIP_CODE is 
'采集系统设备代码';

/*==============================================================*/
/* Table: T_PRO_PROCESS_QC_VALUE                                */
/*==============================================================*/
create table T_PRO_PROCESS_QC_VALUE 
(
   ID                   VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   SALES_ORDER_NO       VARCHAR2(50),
   CUSTOMER_ORDER_NO    VARCHAR2(50),
   CUST_PRODUCT_TYPE    VARCHAR2(200),
   CUST_PRODUCT_SPEC    VARCHAR2(200),
   PRODUCT_CODE         VARCHAR2(50),
   PRODUCT_TYPE         VARCHAR2(200),
   PRODUCT_SPEC         VARCHAR2(200),
   WORK_ORDER_NO        VARCHAR2(50),
   CRAFTS_ID            VARCHAR2(50),
   PROCESS_ID           VARCHAR2(50),
   PROCESS_CODE         VARCHAR2(50),
   SAMPLE_BARCODE       VARCHAR2(50),
   TYPE                 VARCHAR2(50),
   CHECK_ITEM_CODE      VARCHAR2(50),
   EQIP_CODE            VARCHAR2(50),
   QC_VALUE             VARCHAR2(200),
   QC_RESULT            VARCHAR2(200),
   CHECK_EQIP_CODE      VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   COIL_NUM             NUMBER(2),
   constraint PK_PRO_PROCESS_QC_VALUE_ID primary key (ID)
);

comment on table T_PRO_PROCESS_QC_VALUE is
'产品质量检测数据手工录入(QA)';

comment on column T_PRO_PROCESS_QC_VALUE.ID is
'ID';

comment on column T_PRO_PROCESS_QC_VALUE.CONTRACT_NO is
'合同号';

comment on column T_PRO_PROCESS_QC_VALUE.SALES_ORDER_NO is
'客户销售订单编号';

comment on column T_PRO_PROCESS_QC_VALUE.CUSTOMER_ORDER_NO is
'客户生产单号';

comment on column T_PRO_PROCESS_QC_VALUE.CUST_PRODUCT_TYPE is
'客户产品型号';

comment on column T_PRO_PROCESS_QC_VALUE.CUST_PRODUCT_SPEC is
'客户产品规格';

comment on column T_PRO_PROCESS_QC_VALUE.PRODUCT_CODE is
'产品代码';

comment on column T_PRO_PROCESS_QC_VALUE.PRODUCT_TYPE is
'产品规格';

comment on column T_PRO_PROCESS_QC_VALUE.PRODUCT_SPEC is
'产品型号';

comment on column T_PRO_PROCESS_QC_VALUE.WORK_ORDER_NO is
'生产单号';

comment on column T_PRO_PROCESS_QC_VALUE.CRAFTS_ID is
'工艺ID';

comment on column T_PRO_PROCESS_QC_VALUE.PROCESS_ID is
'工序ID';

comment on column T_PRO_PROCESS_QC_VALUE.PROCESS_CODE is
'工序编号';

comment on column T_PRO_PROCESS_QC_VALUE.SAMPLE_BARCODE is
'样品条码';

comment on column T_PRO_PROCESS_QC_VALUE.TYPE is
'检验类型';

comment on column T_PRO_PROCESS_QC_VALUE.CHECK_ITEM_CODE is
'采集参数';

comment on column T_PRO_PROCESS_QC_VALUE.EQIP_CODE is
'生产设备编号';

comment on column T_PRO_PROCESS_QC_VALUE.QC_VALUE is
'检测值';

comment on column T_PRO_PROCESS_QC_VALUE.QC_RESULT is
'检测结论';

comment on column T_PRO_PROCESS_QC_VALUE.CHECK_EQIP_CODE is
'数据检测设备';

comment on column T_PRO_PROCESS_QC_VALUE.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_QC_VALUE.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_QC_VALUE.MODIFY_USER_CODE is
'更新人';

comment on column T_PRO_PROCESS_QC_VALUE.MODIFY_TIME is
'更新时间';

comment on column T_PRO_PROCESS_QC_VALUE.COIL_NUM is
'线盘号';

/*==============================================================*/
/* Table: T_PRO_PROCESS_RECEIPT                                 */
/*==============================================================*/
create table T_PRO_PROCESS_RECEIPT 
(
   ID                   VARCHAR2(50)         not null,
   RECEIPT_CODE         VARCHAR2(50),
   SUB_RECEIPT_CODE     VARCHAR2(50),
   RECEIPT_NAME         VARCHAR2(200)        not null,
   SUB_RECEIPT_NAME     VARCHAR2(200)        not null,
   RECEIPT_TARGET_VALUE VARCHAR2(200),
   RECEIPT_MAX_VALUE    VARCHAR2(200),
   RECEIPT_MIN_VALUE    VARCHAR2(200),
   DATA_TYPE            VARCHAR2(50),
   DATA_UNIT            VARCHAR2(50),
   HAS_PIC              CHAR(1)              default '0' not null,
   MARKS                VARCHAR2(4000),
   NEED_DA              CHAR(1)              default '0' not null,
   NEED_IS              CHAR(1),
   NEED_SHOW            CHAR(1)              default '0' not null,
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   NEED_ALARM           CHAR(1)              default '1' not null,
   VALUE_DOMAIN         VARCHAR2(500),
   EQIP_LIST_ID         VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   FREQUENCE            NUMERIC(6,2),
   AC_EQUIP_CODE        VARCHAR2(50),
   constraint PK_PRO_PRO_RECEIPT_ID primary key (ID)
);

comment on table T_PRO_PROCESS_RECEIPT is
'产品工艺参数';

comment on column T_PRO_PROCESS_RECEIPT.ID is
'ID';

comment on column T_PRO_PROCESS_RECEIPT.RECEIPT_CODE is
'工艺参数CODE';

comment on column T_PRO_PROCESS_RECEIPT.SUB_RECEIPT_CODE is
'工艺参数子CODE';

comment on column T_PRO_PROCESS_RECEIPT.RECEIPT_NAME is
'工艺参数名称';

comment on column T_PRO_PROCESS_RECEIPT.SUB_RECEIPT_NAME is
'工艺参数子名称';

comment on column T_PRO_PROCESS_RECEIPT.RECEIPT_TARGET_VALUE is
'参数目标值';

comment on column T_PRO_PROCESS_RECEIPT.RECEIPT_MAX_VALUE is
'参数上限';

comment on column T_PRO_PROCESS_RECEIPT.RECEIPT_MIN_VALUE is
'参数下限';

comment on column T_PRO_PROCESS_RECEIPT.DATA_TYPE is
'参数数据类型';

comment on column T_PRO_PROCESS_RECEIPT.DATA_UNIT is
'参数单位';

comment on column T_PRO_PROCESS_RECEIPT.HAS_PIC is
'是否有附件';

comment on column T_PRO_PROCESS_RECEIPT.MARKS is
'备注';

comment on column T_PRO_PROCESS_RECEIPT.NEED_DA is
'是否需要数采';

comment on column T_PRO_PROCESS_RECEIPT.NEED_IS is
'是否需要下发';

comment on column T_PRO_PROCESS_RECEIPT.NEED_SHOW is
'是否需要在终端显示';

comment on column T_PRO_PROCESS_RECEIPT.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_RECEIPT.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_RECEIPT.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PROCESS_RECEIPT.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PROCESS_RECEIPT.NEED_ALARM is
'超差是否报警';

comment on column T_PRO_PROCESS_RECEIPT.VALUE_DOMAIN is
'值域';

comment on column T_PRO_PROCESS_RECEIPT.EQIP_LIST_ID is
'工序生产线ID';

comment on column T_PRO_PROCESS_RECEIPT.EQUIP_CODE is
'设备CODE';

comment on column T_PRO_PROCESS_RECEIPT.FREQUENCE is
'检测频率';

comment on column T_PRO_PROCESS_RECEIPT.AC_EQUIP_CODE is
'采集系统设备代码';

/*==============================================================*/
/* Table: T_PRO_PROCESS_RECEIPT_VALUE                           */
/*==============================================================*/
create table T_PRO_PROCESS_RECEIPT_VALUE 
(
   ID                   VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   SALES_ORDER_NO       VARCHAR2(50),
   CUSTOMER_ORDER_NO    VARCHAR2(50),
   CUST_PRODUCT_TYPE    VARCHAR2(200),
   CUST_PRODUCT_SPEC    VARCHAR2(200),
   PRODUCT_CODE         VARCHAR2(50),
   PRODUCT_TYPE         VARCHAR2(200),
   PRODUCT_SPEC         VARCHAR2(200),
   WORK_ORDER_NO        VARCHAR2(50),
   CRAFTS_ID            VARCHAR2(50),
   PROCESS_ID           VARCHAR2(50)         not null,
   PROCESS_CODE         VARCHAR2(50),
   SAMPLE_BARCODE       VARCHAR2(50),
   RECEIPT_CODE         VARCHAR2(50),
   EQIP_CODE            VARCHAR2(50),
   RECEIPT_VALUE        VARCHAR2(200),
   QC_RESULT            VARCHAR2(200),
   CHECK_EQIP_CODE      VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_PRO_PRO_RECEIPT_VALUE_ID primary key (ID)
);

comment on table T_PRO_PROCESS_RECEIPT_VALUE is
'产品工艺监控数据手工录入';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.ID is
'ID';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CONTRACT_NO is
'合同号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.SALES_ORDER_NO is
'客户销售订单编号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CUSTOMER_ORDER_NO is
'客户生产单号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CUST_PRODUCT_TYPE is
'客户产品型号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CUST_PRODUCT_SPEC is
'客户产品规格';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.PRODUCT_CODE is
'产品代码';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.PRODUCT_TYPE is
'产品规格';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.PRODUCT_SPEC is
'产品型号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.WORK_ORDER_NO is
'生产单号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CRAFTS_ID is
'工艺ID';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.PROCESS_ID is
'工序ID';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.PROCESS_CODE is
'工序编号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.SAMPLE_BARCODE is
'样品条码';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.RECEIPT_CODE is
'采集参数';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.EQIP_CODE is
'生产设备编号';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.RECEIPT_VALUE is
'检测值';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.QC_RESULT is
'检测结论';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CHECK_EQIP_CODE is
'数据检测设备';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.CREATE_TIME is
'创建时间';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.MODIFY_USER_CODE is
'更新人';

comment on column T_PRO_PROCESS_RECEIPT_VALUE.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_PRO_PRODUCT_CRAFTS                                  */
/*==============================================================*/
create table T_PRO_PRODUCT_CRAFTS 
(
   ID                   VARCHAR2(50)         not null,
   CRAFTS_CODE          VARCHAR2(50),
   CRAFTS_NAME          VARCHAR2(200)        not null,
   CRAFTS_CNAME          VARCHAR2(200),
   START_DATE           TIMESTAMP            not null,
   END_DATE             TIMESTAMP,
   CRAFTS_VERSION       NUMERIC(12, 0),
   PRODUCT_CODE         VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   IS_DEFAULT           CHAR(1)              default '1' not null,
   constraint PK_PRO_PRODUCT_CRAFTS_ID primary key (ID)
);

comment on table T_PRO_PRODUCT_CRAFTS is
'产品工艺定义';

comment on column T_PRO_PRODUCT_CRAFTS.ID is
'ID';

comment on column T_PRO_PRODUCT_CRAFTS.CRAFTS_CODE is
'工艺代码';

comment on column T_PRO_PRODUCT_CRAFTS.CRAFTS_NAME is
'工艺名称';

comment on column T_PRO_PRODUCT_CRAFTS.CRAFTS_CNAME is
'工艺别名';

comment on column T_PRO_PRODUCT_CRAFTS.START_DATE is
'工艺生效时间';

comment on column T_PRO_PRODUCT_CRAFTS.END_DATE is
'工艺失效时间';

comment on column T_PRO_PRODUCT_CRAFTS.CRAFTS_VERSION is
'工艺版本号';

comment on column T_PRO_PRODUCT_CRAFTS.PRODUCT_CODE is
'产品代码';

comment on column T_PRO_PRODUCT_CRAFTS.CREATE_TIME is
'创建时间';

comment on column T_PRO_PRODUCT_CRAFTS.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PRODUCT_CRAFTS.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PRODUCT_CRAFTS.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PRODUCT_CRAFTS.ORG_CODE is
'数据所属组织';

comment on column T_PRO_PRODUCT_CRAFTS.IS_DEFAULT is
'是否默认工艺';

/*==============================================================*/
/* Table: T_PRO_PRODUCT_PROCESS                                 */
/*==============================================================*/
create table T_PRO_PRODUCT_PROCESS 
(
   ID                   VARCHAR2(50)         not null,
   PROCESS_CODE         VARCHAR2(50),
   PROCESS_NAME         VARCHAR2(200)        not null,
   SEQ                  NUMERIC(4),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   PRODUCT_CRAFTS_ID    VARCHAR2(50)         not null,
   PROCESS_TIME         NUMERIC(6,2),
   SET_UP_TIME          NUMERIC(6,0),
   SHUT_DOWN_TIME       NUMERIC(6,0),
   FULL_PATH            VARCHAR2(4000),
   NEXT_PROCESS_ID      VARCHAR2(50)         not null,
   SAME_PRODUCT_LINE    CHAR(1)              default '0' not null,
   IS_OPTION            CHAR(1)              default '0' not null,
   IS_DEFAULT_SKIP      CHAR(1)              default '0' not null,
   constraint PK_PRO_PRODUCT_PROCESS_ID primary key (ID)
);

comment on table T_PRO_PRODUCT_PROCESS is
'产品工艺流程';

comment on column T_PRO_PRODUCT_PROCESS.ID is
'ID';

comment on column T_PRO_PRODUCT_PROCESS.PROCESS_CODE is
'加工工序代码';

comment on column T_PRO_PRODUCT_PROCESS.PROCESS_NAME is
'加工工序名称';

comment on column T_PRO_PRODUCT_PROCESS.SEQ is
'加工顺序';

comment on column T_PRO_PRODUCT_PROCESS.CREATE_TIME is
'创建时间';

comment on column T_PRO_PRODUCT_PROCESS.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PRODUCT_PROCESS.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PRODUCT_PROCESS.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PRODUCT_PROCESS.PRODUCT_CRAFTS_ID is
'产品工艺ID';

comment on column T_PRO_PRODUCT_PROCESS.PROCESS_TIME is
'加工时间';

comment on column T_PRO_PRODUCT_PROCESS.SET_UP_TIME is
'前置时间';

comment on column T_PRO_PRODUCT_PROCESS.SHUT_DOWN_TIME is
'后置时间';

comment on column T_PRO_PRODUCT_PROCESS.FULL_PATH is
'工序全路径';

comment on column T_PRO_PRODUCT_PROCESS.NEXT_PROCESS_ID is
'下一工序ID';

comment on column T_PRO_PRODUCT_PROCESS.SAME_PRODUCT_LINE is
'是否与上一道工序同一生产线';

comment on column T_PRO_PRODUCT_PROCESS.IS_OPTION is
'是否可选';

comment on column T_PRO_PRODUCT_PROCESS.IS_DEFAULT_SKIP is
'是否默认跳过';

/*==============================================================*/
/* Table: T_PRO_PRODUCT_QC_DET                                  */
/*==============================================================*/
create table T_PRO_PRODUCT_QC_DET 
(
   ID                   VARCHAR2(50)         not null,
   QC_TEMP_ID           VARCHAR2(50)         not null,
   QC_RES_ID            VARCHAR2(50)         not null,
   QC_RESULT            VARCHAR2(500),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   STATUS               VARCHAR2(50),
   constraint PK_PRO_PRODUCT_QC_RESULT_ID primary key (ID)
);

comment on table T_PRO_PRODUCT_QC_DET is
'产品QC检验结果明细';

comment on column T_PRO_PRODUCT_QC_DET.ID is
'ID';

comment on column T_PRO_PRODUCT_QC_DET.QC_TEMP_ID is
'检验模板ID';

comment on column T_PRO_PRODUCT_QC_DET.QC_RES_ID is
'检测结果ID';

comment on column T_PRO_PRODUCT_QC_DET.QC_RESULT is
'结论';

comment on column T_PRO_PRODUCT_QC_DET.CREATE_TIME is
'创建时间';

comment on column T_PRO_PRODUCT_QC_DET.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PRODUCT_QC_DET.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PRODUCT_QC_DET.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PRODUCT_QC_DET.ORG_CODE is
'数据所属组织';

comment on column T_PRO_PRODUCT_QC_DET.STATUS is
'状态';

/*==============================================================*/
/* Table: T_PRO_PRODUCT_QC_RES                                  */
/*==============================================================*/
create table T_PRO_PRODUCT_QC_RES 
(
   ID                   VARCHAR2(50)         not null,
   CONCLUSION           VARCHAR2(200),
   SAMPLE_BARCODE       VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   STATUS               VARCHAR2(50),
   constraint PK_PRO_PRODUCT_QC_RES_ID primary key (ID)
);

comment on table T_PRO_PRODUCT_QC_RES is
'产品QC检验结果';

comment on column T_PRO_PRODUCT_QC_RES.ID is
'ID';

comment on column T_PRO_PRODUCT_QC_RES.CONCLUSION is
'检验结论';

comment on column T_PRO_PRODUCT_QC_RES.SAMPLE_BARCODE is
'送检条码';

comment on column T_PRO_PRODUCT_QC_RES.PRODUCT_CODE is
'所属产品';

comment on column T_PRO_PRODUCT_QC_RES.CREATE_TIME is
'录入时间';

comment on column T_PRO_PRODUCT_QC_RES.CREATE_USER_CODE is
'录入人';

comment on column T_PRO_PRODUCT_QC_RES.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PRODUCT_QC_RES.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PRODUCT_QC_RES.ORG_CODE is
'数据所属组织';

comment on column T_PRO_PRODUCT_QC_RES.STATUS is
'状态';

/*==============================================================*/
/* Table: T_PRO_PRODUCT_QC_TEMP                                 */
/*==============================================================*/
create table T_PRO_PRODUCT_QC_TEMP 
(
   ID                   VARCHAR2(50)         not null,
   CODE                 VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   NAME                 VARCHAR2(200)        not null,
   WIRE_REQU            VARCHAR2(500),
   PRE_PROCESS          VARCHAR2(500),
   ENV_PARM             VARCHAR2(500),
   ENV_VALUE            VARCHAR2(500),
   MAT_REQU             VARCHAR2(500),
   EQIP_REQU            VARCHAR2(500),
   CHARACTER_DESC       VARCHAR2(300),
   CHARACTER_VALUE      VARCHAR2(300),
   REF_CONTENT          VARCHAR2(100),
   REMARKS              VARCHAR2(300),
   TTA                  VARCHAR2(300),
   TTB                  VARCHAR2(300),
   TTC                  VARCHAR2(300),
   CREATE_TIME          TIMESTAMP            not null,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   STATUS               VARCHAR2(50),
   constraint PK_PRO_PRODUCT_QC_TEMP_ID primary key (ID)
);

comment on table T_PRO_PRODUCT_QC_TEMP is
'产品QC检验内容模板';

comment on column T_PRO_PRODUCT_QC_TEMP.ID is
'ID';

comment on column T_PRO_PRODUCT_QC_TEMP.CODE is
'CODE';

comment on column T_PRO_PRODUCT_QC_TEMP.PRODUCT_CODE is
'产品CODE';

comment on column T_PRO_PRODUCT_QC_TEMP.NAME is
'实验名称';

comment on column T_PRO_PRODUCT_QC_TEMP.WIRE_REQU is
'样品线要求';

comment on column T_PRO_PRODUCT_QC_TEMP.PRE_PROCESS is
'预处理';

comment on column T_PRO_PRODUCT_QC_TEMP.ENV_PARM is
'环境参数';

comment on column T_PRO_PRODUCT_QC_TEMP.ENV_VALUE is
'实验方法数值';

comment on column T_PRO_PRODUCT_QC_TEMP.MAT_REQU is
'所需材料/试剂';

comment on column T_PRO_PRODUCT_QC_TEMP.EQIP_REQU is
'设备';

comment on column T_PRO_PRODUCT_QC_TEMP.CHARACTER_DESC is
'性能要求特性';

comment on column T_PRO_PRODUCT_QC_TEMP.CHARACTER_VALUE is
'性能要求数值';

comment on column T_PRO_PRODUCT_QC_TEMP.REF_CONTENT is
'参考';

comment on column T_PRO_PRODUCT_QC_TEMP.REMARKS is
'备注';

comment on column T_PRO_PRODUCT_QC_TEMP.TTA is
'预留字段1';

comment on column T_PRO_PRODUCT_QC_TEMP.TTB is
'预留字段2';

comment on column T_PRO_PRODUCT_QC_TEMP.TTC is
'预留字段3';

comment on column T_PRO_PRODUCT_QC_TEMP.CREATE_TIME is
'创建时间';

comment on column T_PRO_PRODUCT_QC_TEMP.CREATE_USER_CODE is
'创建人';

comment on column T_PRO_PRODUCT_QC_TEMP.MODIFY_TIME is
'修改时间';

comment on column T_PRO_PRODUCT_QC_TEMP.MODIFY_USER_CODE is
'修改人';

comment on column T_PRO_PRODUCT_QC_TEMP.ORG_CODE is
'数据所属组织';

comment on column T_PRO_PRODUCT_QC_TEMP.STATUS is
'状态';

/*==============================================================*/
/* Table: T_WIP_DEBUG                                           */
/*==============================================================*/
create table T_WIP_DEBUG 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   DEBUG_TYPE           VARCHAR2(50),
   START_TIME           TIMESTAMP            not null,
   END_TIME             TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_WIP_DEBUG_ID primary key (ID)
);

comment on table T_WIP_DEBUG is
'调试时间';

comment on column T_WIP_DEBUG.ID is
'ID';

comment on column T_WIP_DEBUG.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_DEBUG.DEBUG_TYPE is
'调试类型';

comment on column T_WIP_DEBUG.START_TIME is
'开始调试时间';

comment on column T_WIP_DEBUG.END_TIME is
'结束调试时间';

comment on column T_WIP_DEBUG.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_DEBUG.CREATE_TIME is
'创建时间';

comment on column T_WIP_DEBUG.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_DEBUG.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_WIP_ONOFF_RECORD                                    */
/*==============================================================*/
create table T_WIP_ONOFF_RECORD 
(
   ID                   VARCHAR2(50)         not null,
   USER_CODE            VARCHAR2(50),
   ON_TIME              TIMESTAMP            not null,
   OFF_TIME             TIMESTAMP,
   EXCEPTION_TYPE       VARCHAR2(50),
   CLIENT_NAME          VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   SHIFT_ID             VARCHAR2(50),
   constraint PK_WIP_ONOFF_RECORD_ID primary key (ID)
);

comment on table T_WIP_ONOFF_RECORD is
'刷卡记录';

comment on column T_WIP_ONOFF_RECORD.ID is
'ID';

comment on column T_WIP_ONOFF_RECORD.USER_CODE is
'工号';

comment on column T_WIP_ONOFF_RECORD.ON_TIME is
'刷入时间';

comment on column T_WIP_ONOFF_RECORD.OFF_TIME is
'刷出时间';

comment on column T_WIP_ONOFF_RECORD.EXCEPTION_TYPE is
'异常刷出类型';

comment on column T_WIP_ONOFF_RECORD.CLIENT_NAME is
'终端MAC';

comment on column T_WIP_ONOFF_RECORD.ORG_CODE is
'直属机构编号';

comment on column T_WIP_ONOFF_RECORD.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_ONOFF_RECORD.CREATE_TIME is
'创建时间';

comment on column T_WIP_ONOFF_RECORD.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_ONOFF_RECORD.MODIFY_TIME is
'更新时间';

comment on column T_WIP_ONOFF_RECORD.SHIFT_ID is
'班次ID';

/*==============================================================*/
/* Table: T_WIP_REAL_COST                                       */
/*==============================================================*/
create table T_WIP_REAL_COST 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   MAT_CODE             VARCHAR2(50),
   BATCH_NO             VARCHAR2(50),
   ORG_CODE             VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_WIP_REAL_COST_ID primary key (ID)
);

comment on table T_WIP_REAL_COST is
'实际投入物料';

comment on column T_WIP_REAL_COST.ID is
'ID';

comment on column T_WIP_REAL_COST.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_REAL_COST.MAT_CODE is
'物料代码';

comment on column T_WIP_REAL_COST.BATCH_NO is
'批次号';

comment on column T_WIP_REAL_COST.ORG_CODE is
'直属机构编号';

comment on column T_WIP_REAL_COST.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_REAL_COST.CREATE_TIME is
'创建时间';

comment on column T_WIP_REAL_COST.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_REAL_COST.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_WIP_RECEIPT                                         */
/*==============================================================*/
create table T_WIP_RECEIPT 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_ID        VARCHAR2(50)         not null,
   RECEIPT_ID           VARCHAR2(50),
   RECEIPT_CODE         VARCHAR2(50),
   RECEIPT_NAME         VARCHAR2(200),
   EQUIP_CODE           VARCHAR2(50),
   STATUS               VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   CONFIRM_TIME         TIMESTAMP,
   ISSUED_TIME          TIMESTAMP,
   CREATE_TIME          TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   RECEIPT_TARGET_VALUE VARCHAR2(200),
   RECEIPT_MAX_VALUE    VARCHAR2(200),
   RECEIPT_MIN_VALUE    VARCHAR2(200),
   NEED_ALARM           CHAR(1)              default '1' not null,
   TYPE                 VARCHAR2(50),
   FREQUENCE            NUMERIC(6,2),
   constraint PK_WIP_RECEIPT_ID primary key (ID)
);

comment on table T_WIP_RECEIPT is
'生产下发工艺参数';

comment on column T_WIP_RECEIPT.ID is
'ID';

comment on column T_WIP_RECEIPT.WORK_ORDER_ID is
'生产单ID';

comment on column T_WIP_RECEIPT.RECEIPT_ID is
'工艺参数ID';

comment on column T_WIP_RECEIPT.RECEIPT_CODE is
'工艺参数CODE';

comment on column T_WIP_RECEIPT.RECEIPT_NAME is
'工艺参数名称';

comment on column T_WIP_RECEIPT.EQUIP_CODE is
'设备CODE';

comment on column T_WIP_RECEIPT.STATUS is
'状态: NEW 新建 ,CONFIRM 确认,ISSUED 已下发';

comment on column T_WIP_RECEIPT.ORG_CODE is
'直属机构编号';

comment on column T_WIP_RECEIPT.CONFIRM_TIME is
'确认时间';

comment on column T_WIP_RECEIPT.ISSUED_TIME is
'下发时间';

comment on column T_WIP_RECEIPT.CREATE_TIME is
'创建时间';

comment on column T_WIP_RECEIPT.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_RECEIPT.MODIFY_TIME is
'修改时间';

comment on column T_WIP_RECEIPT.MODIFY_USER_CODE is
'修改人';

comment on column T_WIP_RECEIPT.RECEIPT_TARGET_VALUE is
'参数设定值';

comment on column T_WIP_RECEIPT.RECEIPT_MAX_VALUE is
'参数上限';

comment on column T_WIP_RECEIPT.RECEIPT_MIN_VALUE is
'参数下限';

comment on column T_WIP_RECEIPT.NEED_ALARM is
'超差是否报警';

comment on column T_WIP_RECEIPT.TYPE is
'参数类型';

comment on column T_WIP_RECEIPT.FREQUENCE is
'检测频率';

/*==============================================================*/
/* Table: T_WIP_REPORT                                          */
/*==============================================================*/
create table T_WIP_REPORT 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   SERIAL_NUM           VARCHAR2(50),
   WEIGHT               NUMERIC(12,2),
   COIL_NUM             NUMBER(2),
   STATUS               VARCHAR2(50),
   REPORT_USER_CODE     VARCHAR2(50)         not null,
   REPORT_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   REPORT_LENGTH        NUMERIC(10,2),
   GOOD_LENGTH          NUMERIC(10,2),
   constraint PK_WIP_REPORT_ID primary key (ID)
);

comment on table T_WIP_REPORT is
'报工单';

comment on column T_WIP_REPORT.ID is
'用户ID';

comment on column T_WIP_REPORT.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_REPORT.SERIAL_NUM is
'产出条码';

comment on column T_WIP_REPORT.WEIGHT is
'重量';

comment on column T_WIP_REPORT.COIL_NUM is
'线盘号';

comment on column T_WIP_REPORT.STATUS is
'状态';

comment on column T_WIP_REPORT.REPORT_USER_CODE is
'创建人';

comment on column T_WIP_REPORT.REPORT_TIME is
'创建时间';

comment on column T_WIP_REPORT.ORG_CODE is
'直属机构编号';

comment on column T_WIP_REPORT.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_REPORT.CREATE_TIME is
'创建时间';

comment on column T_WIP_REPORT.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_REPORT.MODIFY_TIME is
'更新时间';

comment on column T_WIP_REPORT.REPORT_LENGTH is
'总长度';

comment on column T_WIP_REPORT.GOOD_LENGTH is
'合格长度';

/*==============================================================*/
/* Table: T_WIP_SCRAP                                           */
/*==============================================================*/
create table T_WIP_SCRAP 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   MAT_CODE             VARCHAR2(50),
   USER_CODE            VARCHAR2(50),
   WEIGHT               NUMERIC(12,2),
   ORG_CODE             VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_WIP_SCRAP_ID primary key (ID)
);

comment on table T_WIP_SCRAP is
'实际料废';

comment on column T_WIP_SCRAP.ID is
'ID';

comment on column T_WIP_SCRAP.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_SCRAP.MAT_CODE is
'物料代码';

comment on column T_WIP_SCRAP.USER_CODE is
'工人工号';

comment on column T_WIP_SCRAP.WEIGHT is
'重量';

comment on column T_WIP_SCRAP.ORG_CODE is
'直属机构编号';

comment on column T_WIP_SCRAP.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_SCRAP.CREATE_TIME is
'创建时间';

comment on column T_WIP_SCRAP.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_SCRAP.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* Table: T_WIP_SECTION                                         */
/*==============================================================*/
create table T_WIP_SECTION 
(
   ID                   VARCHAR2(50)         not null,
   R_ID                 VARCHAR2(50),
   ORDER_ITEM_PRO_DEC_ID VARCHAR2(50),
   PROCESS_PATH         VARCHAR2(4000)       not null,
   PRODUCT_LENGTH       NUMERIC(10,2),
   SECTION_LENGTH       NUMERIC(10,2),
   GOOD_LENGTH          NUMERIC(10,2)        not null,
   SECTION_TYPE         VARCHAR2(50)         not null,
   ORG_CODE             VARCHAR2(50)         not null,
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   WORK_ORDER_NO        VARCHAR2(50)         not null,
   SECTION_LOCAL        NUMERIC(10,2)        not null,
   constraint PK_WIP_SECTION_ID primary key (ID)
);

comment on table T_WIP_SECTION is
'分段长度';

comment on column T_WIP_SECTION.ID is
'ID';

comment on column T_WIP_SECTION.R_ID is
'用户ID';

comment on column T_WIP_SECTION.ORDER_ITEM_PRO_DEC_ID is
'客户订单号';

comment on column T_WIP_SECTION.PROCESS_PATH is
'工序路径';

comment on column T_WIP_SECTION.PRODUCT_LENGTH is
'生产长度';

comment on column T_WIP_SECTION.SECTION_LENGTH is
'分段长度';

comment on column T_WIP_SECTION.GOOD_LENGTH is
'合格长度';

comment on column T_WIP_SECTION.SECTION_TYPE is
'分段类型';

comment on column T_WIP_SECTION.ORG_CODE is
'直属机构编号';

comment on column T_WIP_SECTION.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_SECTION.CREATE_TIME is
'创建时间';

comment on column T_WIP_SECTION.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_SECTION.MODIFY_TIME is
'更新时间';

comment on column T_WIP_SECTION.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_SECTION.SECTION_LOCAL is
'分段位置';

/*==============================================================*/
/* Index: INDEX_WIP_SECION_PRO_DEC_ID                           */
/*==============================================================*/
create index INDEX_WIP_SECION_PRO_DEC_ID on T_WIP_SECTION (
   ORDER_ITEM_PRO_DEC_ID ASC
);

/*==============================================================*/
/* Table: T_WIP_WORK_ORDER                                      */
/*==============================================================*/
create table T_WIP_WORK_ORDER 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   ORG_CODE             VARCHAR2(50)         not null,
   PRE_START_TIME       TIMESTAMP            not null,
   PRE_END_TIME         TIMESTAMP            not null,
   REAL_START_TIME      TIMESTAMP,
   REAL_END_TIME        TIMESTAMP,
   AUDIT_TIME           TIMESTAMP,
   AUDIT_USER_CODE      VARCHAR2(50),
   ORDER_LENGTH         NUMERIC(10,2),
   CANCEL_LENGTH        NUMERIC(10,2),
   STATUS               VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   PROCESS_ID           VARCHAR2(50),
   PROCESS_NAME         VARCHAR2(200),
   HALF_PRODUCT_CODE    VARCHAR2(50),
   IS_DELAYED           CHAR(1)              default '0' not null,
   FIXED_EQUIP_CODE     VARCHAR2(50),
   IS_SKIPED            CHAR(1)              default '0',
   SKIP_REASON          VARCHAR2(400),
   constraint PK_WIP_WORK_ORDER_ID primary key (ID)
);

comment on table T_WIP_WORK_ORDER is
'生产单';

comment on column T_WIP_WORK_ORDER.ID is
'用户ID';

comment on column T_WIP_WORK_ORDER.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_WORK_ORDER.EQUIP_CODE is
'生产设备编号';

comment on column T_WIP_WORK_ORDER.ORG_CODE is
'直属机构编号';

comment on column T_WIP_WORK_ORDER.PRE_START_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER.PRE_END_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER.REAL_START_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER.REAL_END_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER.AUDIT_TIME is
'审核日期';

comment on column T_WIP_WORK_ORDER.AUDIT_USER_CODE is
'审核人';

comment on column T_WIP_WORK_ORDER.ORDER_LENGTH is
'计划生产长度';

comment on column T_WIP_WORK_ORDER.CANCEL_LENGTH is
'总计取消长度';

comment on column T_WIP_WORK_ORDER.STATUS is
'状态';

comment on column T_WIP_WORK_ORDER.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_WORK_ORDER.CREATE_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_WORK_ORDER.MODIFY_TIME is
'更新时间';

comment on column T_WIP_WORK_ORDER.PROCESS_ID is
'工序ID';

comment on column T_WIP_WORK_ORDER.PROCESS_NAME is
'工序名称';

comment on column T_WIP_WORK_ORDER.HALF_PRODUCT_CODE is
'半成品代码';

comment on column T_WIP_WORK_ORDER.IS_DELAYED is
'是否延迟';

comment on column T_WIP_WORK_ORDER.FIXED_EQUIP_CODE is
'固定加工设备';

comment on column T_WIP_WORK_ORDER.IS_SKIPED is
'是否跳过';

comment on column T_WIP_WORK_ORDER.SKIP_REASON is
'跳过原因';

/*==============================================================*/
/* Index: INDEX_WORK_ORDER_ORNO                                 */
/*==============================================================*/
create index INDEX_WORK_ORDER_ORNO on T_WIP_WORK_ORDER (
   WORK_ORDER_NO ASC
);

/*==============================================================*/
/* Table: T_WIP_WORK_ORDER_OPERATE_LOG                          */
/*==============================================================*/
create table T_WIP_WORK_ORDER_OPERATE_LOG 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_NO        VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   PRODUCT_TYPE         VARCHAR2(50),
   PRODUCT_SPEC         VARCHAR2(50),
   OUT_PRODUCT_COLOR    VARCHAR2(50),
   OPERATE_REASON       VARCHAR2(4000),
   OPERATE_TYPE         VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   ORG_CODE             VARCHAR2(50),
   OLD_FLAG             VARCHAR2(50)
);

comment on table T_WIP_WORK_ORDER_OPERATE_LOG is
'生产单操作日志';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.ID is
'ID';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.EQUIP_CODE is
'生产设备编号';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.PRODUCT_TYPE is
'产品型号';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.PRODUCT_SPEC is
'产品规格';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.OUT_PRODUCT_COLOR is
'半成品颜色';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.OPERATE_REASON is
'操作原因';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.OPERATE_TYPE is
'操作类型';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.CREATE_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.MODIFY_USER_CODE is
'修改人';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.MODIFY_TIME is
'修改时间';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.ORG_CODE is
'数据所属组织';

comment on column T_WIP_WORK_ORDER_OPERATE_LOG.OLD_FLAG is
'历史数据标示';

/*==============================================================*/
/* Table: T_WIP_WORK_ORDER_REPORT                               */
/*==============================================================*/
create table T_WIP_WORK_ORDER_REPORT 
(
   ID                   VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   WORK_ORDER_NO        VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   SHIFT_ID             VARCHAR2(50),
   SHIFT_NAME           VARCHAR2(50),
   PRODUCT_TYPE         VARCHAR2(100),
   PRODUCT_SPEC         VARCHAR2(50),
   WORK_HOURS           NUMBER(10,2),
   FINISHED_LENGTH      NUMBER,
   DB_WORKER            VARCHAR2(50),
   FDB_WORKER           VARCHAR2(50),
   FZG_WORKER           VARCHAR2(50),
   COLOR_OR_WORD        VARCHAR2(50),
   JS_THICKNESS_MIN     NUMBER,
   JS_THICKNESS_MAX     NUMBER,
   JS_FRONT_OUTERDIAMETER_MIN NUMBER,
   JS_FRONT_OUTERDIAMETER_MAX NUMBER,
   JS_BACK_OUTERDIAMETER_MIN NUMBER,
   JS_BACK_OUTERDIAMETER_MAX NUMBER,
   TEST_VOLTAGE         NUMBER,
   PUNCTURE_NUM         NUMBER,
   LINE_SPEED           NUMBER,
   OUTER_POSITION       VARCHAR2(50),
   BELT_POSITION        VARCHAR2(50),
   CU_COVER_LEVEL       NUMBER,
   PLAN_LENGTH          NUMBER,
   REAL_LENGTH          NUMBER,
   TESTING              VARCHAR2(50),
   QUALITY              VARCHAR2(50),
   KIND                 VARCHAR2(50),
   PRE_LEAVE            VARCHAR2(50),
   THIS_TAKE            VARCHAR2(50),
   THIS_BACK            VARCHAR2(50),
   CL_OUTERDIAMETER_UP  NUMBER,
   CL_OUTERDIAMETER_DOWN NUMBER,
   PB_OUTERDIAMETER_UP  NUMBER,
   PB_OUTERDIAMETER_DOWN NUMBER,
   PICE_RANGE           NUMBER,
   PB_MAT               VARCHAR2(50),
   RB_QUALITY           VARCHAR2(50),
   COVER_LEVEL          NUMBER,
   OWNER_TESTING        VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   REPORT_DATE          VARCHAR2(50),
   REPORT_TYPE          VARCHAR2(50),
   ORG_CODE             VARCHAR2(50),
   constraint PK_T_WIP_WORK_ORDER_REPORT primary key (ID)
);

comment on table T_WIP_WORK_ORDER_REPORT is
'生产单记录报表';

comment on column T_WIP_WORK_ORDER_REPORT.ID is
'ID';

comment on column T_WIP_WORK_ORDER_REPORT.CONTRACT_NO is
'订单号/合同号';

comment on column T_WIP_WORK_ORDER_REPORT.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_WORK_ORDER_REPORT.EQUIP_CODE is
'机床';

comment on column T_WIP_WORK_ORDER_REPORT.SHIFT_ID is
'班次';

comment on column T_WIP_WORK_ORDER_REPORT.SHIFT_NAME is
'班次名称';

comment on column T_WIP_WORK_ORDER_REPORT.PRODUCT_TYPE is
'型号';

comment on column T_WIP_WORK_ORDER_REPORT.PRODUCT_SPEC is
'规格';

comment on column T_WIP_WORK_ORDER_REPORT.WORK_HOURS is
'工时';

comment on column T_WIP_WORK_ORDER_REPORT.FINISHED_LENGTH is
'长度';

comment on column T_WIP_WORK_ORDER_REPORT.DB_WORKER is
'挡班';

comment on column T_WIP_WORK_ORDER_REPORT.FDB_WORKER is
'附挡班';

comment on column T_WIP_WORK_ORDER_REPORT.FZG_WORKER is
'辅助工';

comment on column T_WIP_WORK_ORDER_REPORT.COLOR_OR_WORD is
'色别或字码';

comment on column T_WIP_WORK_ORDER_REPORT.JS_THICKNESS_MIN is
'挤塑厚度/min';

comment on column T_WIP_WORK_ORDER_REPORT.JS_THICKNESS_MAX is
'挤塑厚度/max';

comment on column T_WIP_WORK_ORDER_REPORT.JS_FRONT_OUTERDIAMETER_MIN is
'挤塑前外径/min';

comment on column T_WIP_WORK_ORDER_REPORT.JS_FRONT_OUTERDIAMETER_MAX is
'挤塑前外径/max';

comment on column T_WIP_WORK_ORDER_REPORT.JS_BACK_OUTERDIAMETER_MIN is
'挤塑后外径/min';

comment on column T_WIP_WORK_ORDER_REPORT.JS_BACK_OUTERDIAMETER_MAX is
'挤塑后外径/max';

comment on column T_WIP_WORK_ORDER_REPORT.TEST_VOLTAGE is
'试验电压';

comment on column T_WIP_WORK_ORDER_REPORT.PUNCTURE_NUM is
'击穿次数';

comment on column T_WIP_WORK_ORDER_REPORT.LINE_SPEED is
'线速度m/s';

comment on column T_WIP_WORK_ORDER_REPORT.OUTER_POSITION is
'外层方向';

comment on column T_WIP_WORK_ORDER_REPORT.BELT_POSITION is
'包带方向';

comment on column T_WIP_WORK_ORDER_REPORT.CU_COVER_LEVEL is
'铜丝覆盖率或铜带重叠率';

comment on column T_WIP_WORK_ORDER_REPORT.PLAN_LENGTH is
'计划长度';

comment on column T_WIP_WORK_ORDER_REPORT.REAL_LENGTH is
'实际长度';

comment on column T_WIP_WORK_ORDER_REPORT.TESTING is
'检验';

comment on column T_WIP_WORK_ORDER_REPORT.QUALITY is
'质量';

comment on column T_WIP_WORK_ORDER_REPORT.KIND is
'种类';

comment on column T_WIP_WORK_ORDER_REPORT.PRE_LEAVE is
'上班盘存';

comment on column T_WIP_WORK_ORDER_REPORT.THIS_TAKE is
'本班领用';

comment on column T_WIP_WORK_ORDER_REPORT.THIS_BACK is
'本班退用';

comment on column T_WIP_WORK_ORDER_REPORT.CL_OUTERDIAMETER_UP is
'成缆外径mm/上';

comment on column T_WIP_WORK_ORDER_REPORT.CL_OUTERDIAMETER_DOWN is
'成缆外径mm/下';

comment on column T_WIP_WORK_ORDER_REPORT.PB_OUTERDIAMETER_UP is
'屏蔽外径mm/上';

comment on column T_WIP_WORK_ORDER_REPORT.PB_OUTERDIAMETER_DOWN is
'屏蔽外径mm/下';

comment on column T_WIP_WORK_ORDER_REPORT.PICE_RANGE is
'节距';

comment on column T_WIP_WORK_ORDER_REPORT.PB_MAT is
'屏蔽材料';

comment on column T_WIP_WORK_ORDER_REPORT.RB_QUALITY is
'绕包质量';

comment on column T_WIP_WORK_ORDER_REPORT.COVER_LEVEL is
'搭盖率%';

comment on column T_WIP_WORK_ORDER_REPORT.OWNER_TESTING is
'产品自检';

comment on column T_WIP_WORK_ORDER_REPORT.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_WORK_ORDER_REPORT.CREATE_TIME is
'创建时间';

comment on column T_WIP_WORK_ORDER_REPORT.MODIFY_TIME is
'修改时间';

comment on column T_WIP_WORK_ORDER_REPORT.MODIFY_USER_CODE is
'修改人';

comment on column T_WIP_WORK_ORDER_REPORT.REPORT_DATE is
'查询日期';

comment on column T_WIP_WORK_ORDER_REPORT.REPORT_TYPE is
'报表类型';

comment on column T_WIP_WORK_ORDER_REPORT.ORG_CODE is
'直属机构编号';

/*==============================================================*/
/* Table: T_WIP_WORTH                                           */
/*==============================================================*/
create table T_WIP_WORTH 
(
   ID                   VARCHAR2(50)         not null,
   MONEY                NUMERIC(12, 2),
   PIC_PATH             VARCHAR2(200),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_WIP_WORTH_ID primary key (ID)
);

comment on table T_WIP_WORTH is
'金额等级';

comment on column T_WIP_WORTH.ID is
'ID';

comment on column T_WIP_WORTH.MONEY is
'金额';

comment on column T_WIP_WORTH.PIC_PATH is
'图片路径';

comment on column T_WIP_WORTH.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_WORTH.CREATE_TIME is
'创建时间';

comment on column T_WIP_WORTH.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_WORTH.MODIFY_TIME is
'更新时间';

/*==============================================================*/
/* View: V_PLAN_DEATIL                                          */
/*==============================================================*/
create or replace view V_PLAN_DEATIL as
select saord.CONTRACT_NO, ord.CUSTOMER_ORDER_NO,ord.CUSTOMER_OA_DATE,ord.OA_DATE,ord.PLAN_START_DATE as ORDER_PLAN_START_DATE,ord.org_code,
item.PRODUCT_CODE,item.SUB_OA_DATE,item.ORDER_LENGTH,
prodec.PROCESS_NAME,prodec.PROCESS_CODE,
prodec.CRAFTS_ID,prodec.LATEST_START_DATE,prodec.LATEST_FINISH_DATE ,prodec.EQUIP_CODE,prodec.id as ITEM_PRO_DEC_ID,HALF_PRODUCT_CODE
from 
T_ORD_SALES_ORDER  saord,
T_PLA_CUSTOMER_ORDER ord,
T_PLA_CUSTOMER_ORDER_ITEM item, 
T_PLA_CUSTOMER_ORDER_ITEM_DEC cudec,
T_PLA_CU_ORDER_ITEM_PRO_DEC prodec
where 
saord.id=ord.SALES_ORDER_ID and
ord.id=item.CUSTOMER_ORDER_ID
and   item.id= cudec.ORDER_ITEM_ID
and cudec.id=prodec.ORDER_ITEM_DEC_ID

with read only;

comment on column V_PLAN_DEATIL.CONTRACT_NO is
'合同号';

comment on column V_PLAN_DEATIL.CUSTOMER_ORDER_NO is
'生产单号';

comment on column V_PLAN_DEATIL.CUSTOMER_OA_DATE is
'客户要求交货期';

comment on column V_PLAN_DEATIL.OA_DATE is
'订单交货期';

comment on column V_PLAN_DEATIL.ORDER_PLAN_START_DATE is
'计划开工日期';

comment on column V_PLAN_DEATIL.ORG_CODE is
'订单所属组织';

comment on column V_PLAN_DEATIL.PRODUCT_CODE is
'产品编码';

comment on column V_PLAN_DEATIL.SUB_OA_DATE is
'订单明细OA';

comment on column V_PLAN_DEATIL.ORDER_LENGTH is
'计划长度';

comment on column V_PLAN_DEATIL.PROCESS_NAME is
'工序名称';

comment on column V_PLAN_DEATIL.PROCESS_CODE is
'工序CODE';

comment on column V_PLAN_DEATIL.CRAFTS_ID is
'工艺ID';

comment on column V_PLAN_DEATIL.LATEST_START_DATE is
'工序最晚开始时间';

comment on column V_PLAN_DEATIL.LATEST_FINISH_DATE is
'工序最晚结束时间';

comment on column V_PLAN_DEATIL.EQUIP_CODE is
'加工设备';

comment on column V_PLAN_DEATIL.ITEM_PRO_DEC_ID is
'ID';

comment on column V_PLAN_DEATIL.HALF_PRODUCT_CODE is
'半成品代码';

/*==============================================================*/
/* View: V_PROCESS_EQUIP                                        */
/*==============================================================*/
create or replace view V_PROCESS_EQUIP as
select qc.PROCESS_ID,qc.CHECK_ITEM_CODE as ITEM_CODE,qc.CHECK_ITEM_NAME as ITEM_NAME,qc.FREQUENCE,qc.NEED_DA,qc.ITEM_TARGET_VALUE as TARGET_VALUE,qc.ITEM_MAX_VALUE as MAX_VALUE,qc.ITEM_MIN_VALUE as MIN_VALUE ,qc.DATA_TYPE,qc.DATA_UNIT,qcep.EQUIP_CODE,'QA_RECEIPT' as type
 from T_PRO_PROCESS_QC qc, T_PRO_PROCESS_QC_EQIP qcep where qcep.QC_ID=qc.id
union
 select eqp.PROCESS_ID,rec.RECEIPT_CODE as ITEM_CODE,rec.RECEIPT_NAME  as ITEM_NAME,rec.FREQUENCE,rec.NEED_DA,rec.RECEIPT_TARGET_VALUE as TARGET_VALUE,rec.RECEIPT_MAX_VALUE  as MAX_VALUE,rec.RECEIPT_MIN_VALUE as MIN_VALUE ,rec.DATA_TYPE,rec.DATA_UNIT,rec.EQUIP_CODE ,'PROCESS_RECEIPT' as type
 from T_PRO_PROCESS_RECEIPT rec,T_PRO_EQIP_LIST eqp where rec.EQIP_LIST_ID=eqp.id

 
with read only;

comment on column V_PROCESS_EQUIP.PROCESS_ID is
'加工工序';

comment on column V_PROCESS_EQUIP.ITEM_CODE is
'检测项CODE';

comment on column V_PROCESS_EQUIP.ITEM_NAME is
'检测项名称';

comment on column V_PROCESS_EQUIP.FREQUENCE is
'检测频率';

comment on column V_PROCESS_EQUIP.NEED_DA is
'是否需要数采';

comment on column V_PROCESS_EQUIP.TARGET_VALUE is
'参数目标值';

comment on column V_PROCESS_EQUIP.MAX_VALUE is
'参数上限';

comment on column V_PROCESS_EQUIP.MIN_VALUE is
'参数下限';

comment on column V_PROCESS_EQUIP.DATA_TYPE is
'参数数据类型';

comment on column V_PROCESS_EQUIP.DATA_UNIT is
'参数单位';

comment on column V_PROCESS_EQUIP.EQUIP_CODE is
'设备CODE';

comment on column V_PROCESS_EQUIP.TYPE is
'TYPE';

alter table T_BAS_BIZ_INFO
   add constraint FK_BAS_BIZ_ENTITY_TYPE foreign key (ENTITY_ID)
      references T_BAS_ENTITY_INFO (ID);

alter table T_BAS_BIZ_LOG
   add constraint FK_BAS_BIZ_LOG_RE_BIZ_TYPE foreign key (BIZ_ID)
      references T_BAS_BIZ_INFO (ID);

alter table T_BAS_EQIP_CAL_SHIFT
   add constraint FK_EQCA_SHIFT_EQCA_ID foreign key (EQIP_CALENDAR_ID)
      references T_BAS_EQIP_CALENDAR (ID);

alter table T_BAS_EQIP_CAL_SHIFT
   add constraint FK_EQCA_SHIFT_SHIFT_ID foreign key (WORK_SHIFT_ID)
      references T_BAS_WORK_SHIFT (ID);

alter table T_BAS_MES_CLIENT_MAN_EQIP
   add constraint FK_MES_CLI_MAN_EQIP_CLI_ID foreign key (MES_CLIENT_ID)
      references T_BAS_MES_CLIENT (ID);

alter table T_BAS_MES_CLIENT_MAN_EQIP
   add constraint FK_MES_CLI_MAN_EQIP_EQIP_ID foreign key (EQIP_ID)
      references T_FAC_EQIP_INFO (ID);

alter table T_BAS_ROLE_EQIP
   add constraint FK_ROLE_EQIP_EQIP_ID foreign key (EQIP_INFO_ID)
      references T_FAC_EQIP_INFO (ID);

alter table T_BAS_ROLE_EQIP
   add constraint FK_ROLE_EQIP_ROLE_ID foreign key (ROLE_ID)
      references T_BAS_ROLE (ID);

alter table T_BAS_ROLE_RESOURCE
   add constraint FK_T_BAS_RO_FK_RESOUR_T_BAS_RE foreign key (RESOURCE_ID)
      references T_BAS_RESOURCE (ID);

alter table T_BAS_ROLE_RESOURCE
   add constraint FK_T_BAS_RO_FK_ROLE_R_T_BAS_RO foreign key (ROLE_ID)
      references T_BAS_ROLE (ID);

alter table T_BAS_USER_ROLE
   add constraint FK_T_BAS_US_FK_ROLE_U_T_BAS_RO foreign key (ROLE_ID)
      references T_BAS_ROLE (ID);

alter table T_BAS_USER_ROLE
   add constraint FK_T_BAS_US_FK_USER_U_T_BAS_US foreign key (USER_ID)
      references T_BAS_USER (ID);

alter table T_BAS_WEEK_CALENDAR_SHIFT
   add constraint FK_WECA_SHIFT_SHIFT_ID foreign key (WORK_SHIFT_ID)
      references T_BAS_WORK_SHIFT (ID);

alter table T_BAS_WEEK_CALENDAR_SHIFT
   add constraint FK_WECA_SHIFT_WECA_ID foreign key (WEEK_CALENDAR_ID)
      references T_BAS_WEEK_CALENDAR (ID);

alter table T_EVE_EVENT_INFO
   add constraint FK_EVENT_EVE_TYPE_ID foreign key (EVENT_TYPE_ID)
      references T_EVE_EVENT_TYPE (ID);

alter table T_EVE_EVENT_PROCESS
   add constraint FK_EVN_PROC_EVN_PROC_TYPE foreign key (EVENT_TYPE_ID)
      references T_EVE_EVENT_TYPE (ID);

alter table T_EVE_EVENT_PROCESSER
   add constraint FK_EV_PROER_EVE_PROCE_ID foreign key (EVENT_PROCESS_ID)
      references T_EVE_EVENT_PROCESS (ID);

alter table T_FAC_EQUIP_MAINTAIN_STATE
   add constraint FK_T_FAC_EQ_REFERENCE_T_EVE_EV foreign key (EVENT_INFO_ID)
      references T_EVE_EVENT_INFO (ID);

alter table T_FAC_MAINTAIN_ITEM
   add constraint FK_T_FAC_MA_FK_ITEM_T_T_FAC_MA foreign key (TEMP_ID)
      references T_FAC_MAINTAIN_TEMPLATE (ID);

alter table T_FAC_MAINTAIN_RECORD
   add constraint FK_T_FAC_MA_REFERENCE_T_FAC_MA foreign key (TMPL_ID)
      references T_FAC_MAINTAIN_TEMPLATE (ID);

alter table T_FAC_MAINTAIN_RECORD_ITEM
   add constraint FK_T_FAC_MA_FK_ITEM_R_T_FAC_MA foreign key (RECORD_ID)
      references T_FAC_MAINTAIN_RECORD (ID);

alter table T_INV_INVENTORY
   add constraint FK_INV_LOCATION_ID foreign key (LOCATION_ID)
      references T_INV_LOCATION (ID);

alter table T_INV_INVENTORY
   add constraint FK_INV_WAREHOUSE_ID foreign key (WAREHOUSE_ID)
      references T_INV_WAREHOUSE (ID);

alter table T_INV_INVENTORY_DETAIL
   add constraint FK_INV_DETAIL_INV_ID foreign key (INVENTORY_ID)
      references T_INV_INVENTORY (ID);

alter table T_INV_LOCATION
   add constraint FK_LOCATION_WAREHOUSE_ID foreign key (WAREHOUSE_ID)
      references T_INV_WAREHOUSE (ID);

alter table T_INV_MAT
   add constraint FK_MAT_MAT_TEMPLET_ID foreign key (TEMPLET_ID)
      references T_INV_TEMPLET (ID);

alter table T_INV_MAT_PROP
   add constraint FK_MAT_PROP_MAT_ID foreign key (MAT_ID)
      references T_INV_MAT (ID);

alter table T_INV_MAT_PROP
   add constraint FK_MAT_PROP_MAT_TEMP_DETA_ID foreign key (TEMPLET_DETAIL_ID)
      references T_INV_TEMPLET_DETAIL (ID);

alter table T_INV_TEMPLET_DETAIL
   add constraint FK_TEMPLE_DETAIL_TEMPLE_ID foreign key (TEMPLET_ID)
      references T_INV_TEMPLET (ID);

alter table T_ORD_SALES_ORDER_ITEM
   add constraint FK_SALE_ORD_ITEM_SALE_ORDER_ID foreign key (SALES_ORDER_ID)
      references T_ORD_SALES_ORDER (ID);

alter table T_PLA_CUSTOMER_ORDER
   add constraint FK_CU_ORDER_SA_ORDER_ID foreign key (SALES_ORDER_ID)
      references T_ORD_SALES_ORDER (ID);

alter table T_PLA_CUSTOMER_ORDER_ITEM
   add constraint FK_CU_ORDER_ITEM_SA_OR_ITEM_ID foreign key (SALES_ORDER_ITEM_ID)
      references T_ORD_SALES_ORDER_ITEM (ID);

alter table T_PLA_CUSTOMER_ORDER_ITEM
   add constraint FK_CU_ORD_ITEM_CU_ORD_ID foreign key (CUSTOMER_ORDER_ID)
      references T_PLA_CUSTOMER_ORDER (ID);

alter table T_PLA_CUSTOMER_ORDER_ITEM_DEC
   add constraint FK_CU_ORDER_DEC_CU_ORDER_ID foreign key (ORDER_ITEM_ID)
      references T_PLA_CUSTOMER_ORDER_ITEM (ID);

alter table T_PLA_CU_ORDER_ITEM_PRO_DEC
   add constraint FK_CU_OR_ITEM_PRO_DEC_ID foreign key (ORDER_ITEM_DEC_ID)
      references T_PLA_CUSTOMER_ORDER_ITEM_DEC (ID);

alter table T_PLA_HIGH_PRIORITY_ORDER_ITEM
   add constraint FK_PLA_HI_PRI_PLA_ORDER foreign key (ID)
      references T_PLA_CUSTOMER_ORDER (ID);

alter table T_PLA_HIGH_PRIORITY_PRO_DEC
   add constraint FK_T_PLA_HI_REFERENCE_T_PLA_CU foreign key (ID)
      references T_PLA_CU_ORDER_ITEM_PRO_DEC (ID);

alter table T_PLA_MRP
   add constraint FK_MRP_WO_ORD_ID_WO_ORD foreign key (WORK_ORDER_ID)
      references T_WIP_WORK_ORDER (ID);

alter table T_PLA_ORDER_TASK
   add constraint FK_ORDER_TASK_PRO_DEC_ID foreign key (ORDER_ITEM_PRO_DEC_ID)
      references T_PLA_CU_ORDER_ITEM_PRO_DEC (ID);

alter table T_PLA_TOOLES_RP
   add constraint FK_TOOLES_RP_ID_WO foreign key (WORK_ORDER_ID)
      references T_WIP_WORK_ORDER (ID);

alter table T_PRO_EQIP_LIST
   add constraint FK_PRO_EQIP_LIST_PRO_ID foreign key (PROCESS_ID)
      references T_PRO_PRODUCT_PROCESS (ID);

alter table T_PRO_PROCESS_IN_OUT
   add constraint FK_PRO_INOUT_PRO_PROCESS_ID foreign key (PRODUCT_PROCESS_ID)
      references T_PRO_PRODUCT_PROCESS (ID);

alter table T_PRO_PROCESS_QC
   add constraint FK_PROCESS_QC_PROCESS_ID foreign key (PROCESS_ID)
      references T_PRO_PRODUCT_PROCESS (ID);

alter table T_PRO_PROCESS_QC_EQIP
   add constraint FK_T_PRO_PR_REFERENCE_T_PRO_PR foreign key (QC_ID)
      references T_PRO_PROCESS_QC (ID);

alter table T_PRO_PROCESS_RECEIPT
   add constraint FK_PRO_RECP_PRO_ID foreign key (EQIP_LIST_ID)
      references T_PRO_EQIP_LIST (ID);

alter table T_PRO_PRODUCT_PROCESS
   add constraint FK_PRO_PORCESS_PRO_CRAFTS_ID foreign key (PRODUCT_CRAFTS_ID)
      references T_PRO_PRODUCT_CRAFTS (ID);

alter table T_PRO_PRODUCT_QC_DET
   add constraint FK_PRO_QC_DE_REF_QC_TEMP foreign key (QC_TEMP_ID)
      references T_PRO_PRODUCT_QC_TEMP (ID);

alter table T_PRO_PRODUCT_QC_DET
   add constraint FK_QC_DE_REF_PRO_QC_RE foreign key (QC_RES_ID)
      references T_PRO_PRODUCT_QC_RES (ID);

DROP TABLE QRTZ_CALENDARS;
DROP TABLE QRTZ_FIRED_TRIGGERS;
DROP TABLE QRTZ_TRIGGER_LISTENERS;
DROP TABLE QRTZ_BLOB_TRIGGERS;
DROP TABLE QRTZ_CRON_TRIGGERS;
DROP TABLE QRTZ_SIMPLE_TRIGGERS;
DROP TABLE QRTZ_TRIGGERS;
DROP TABLE QRTZ_JOB_LISTENERS;
DROP TABLE QRTZ_JOB_DETAILS;
DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE QRTZ_LOCKS;
DROP TABLE QRTZ_SCHEDULER_STATE;


CREATE TABLE QRTZ_JOB_DETAILS
  (
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL, 
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    IS_STATEFUL VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP)
);
CREATE TABLE QRTZ_JOB_LISTENERS
  (
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    JOB_LISTENER VARCHAR2(200) NOT NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP,JOB_LISTENER),
    FOREIGN KEY (JOB_NAME,JOB_GROUP) 
	REFERENCES QRTZ_JOB_DETAILS(JOB_NAME,JOB_GROUP)
);
CREATE TABLE QRTZ_TRIGGERS
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (JOB_NAME,JOB_GROUP) 
	REFERENCES QRTZ_JOB_DETAILS(JOB_NAME,JOB_GROUP) 
);
CREATE TABLE QRTZ_SIMPLE_TRIGGERS
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE QRTZ_CRON_TRIGGERS
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE QRTZ_BLOB_TRIGGERS
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
        REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE QRTZ_TRIGGER_LISTENERS
  (
    TRIGGER_NAME  VARCHAR2(200) NOT NULL, 
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    TRIGGER_LISTENER VARCHAR2(200) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE QRTZ_CALENDARS
  (
    CALENDAR_NAME  VARCHAR2(200) NOT NULL, 
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (CALENDAR_NAME)
);
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
  (
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL, 
    PRIMARY KEY (TRIGGER_GROUP)
);
CREATE TABLE QRTZ_FIRED_TRIGGERS 
  (
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_STATEFUL VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    PRIMARY KEY (ENTRY_ID)
);
CREATE TABLE QRTZ_SCHEDULER_STATE 
  (
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    PRIMARY KEY (INSTANCE_NAME)
);
CREATE TABLE QRTZ_LOCKS
  (
    LOCK_NAME  VARCHAR2(40) NOT NULL, 
    PRIMARY KEY (LOCK_NAME)
);

create table T_WIP_SPARK_REPAIR 
(
   ID                   VARCHAR2(50)         not null,
   CONTRACT_NO          VARCHAR2(50),
   WORK_ORDER_NO        VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   SPARK_POSITION       NUMERIC(10,2),
   EQUIP_CODE           VARCHAR2(50),
   REPAIR_USER_NAME     VARCHAR2(50),
   REPAIR_TYPE          VARCHAR(50),
   STATUS               VARCHAR(50),
   REPAIR_TIME          TIMESTAMP,
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP,
   constraint PK_T_WIP_SPARK_REPAIR primary key (ID)
);

comment on table T_WIP_SPARK_REPAIR is
'火花修复记录';

comment on column T_WIP_SPARK_REPAIR.ID is
'ID';

comment on column T_WIP_SPARK_REPAIR.CONTRACT_NO is
'合同号';

comment on column T_WIP_SPARK_REPAIR.WORK_ORDER_NO is
'生产单号';

comment on column T_WIP_SPARK_REPAIR.PRODUCT_CODE is
'产品代码';

comment on column T_WIP_SPARK_REPAIR.SPARK_POSITION is
'火花位置';

comment on column T_WIP_SPARK_REPAIR.EQUIP_CODE is
'设备代码';

comment on column T_WIP_SPARK_REPAIR.REPAIR_USER_NAME is
'修复人';

comment on column T_WIP_SPARK_REPAIR.REPAIR_TYPE is
'修复方式';

comment on column T_WIP_SPARK_REPAIR.STATUS is
'状态';

comment on column T_WIP_SPARK_REPAIR.REPAIR_TIME is
'修复时间';

comment on column T_WIP_SPARK_REPAIR.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_SPARK_REPAIR.CREATE_TIME is
'创建时间';

comment on column T_WIP_SPARK_REPAIR.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_SPARK_REPAIR.MODIFY_TIME is
'更新时间';

drop table T_WIP_WO_EQUIP_RELATION cascade constraints;

/*==============================================================*/
/* Table: T_WIP_WO_EQUIP_RELATION                               */
/*==============================================================*/
create table T_WIP_WO_EQUIP_RELATION 
(
   ID                   VARCHAR2(50)         not null,
   WORK_ORDER_ID       VARCHAR2(50),
   EQUIP_CODE           VARCHAR2(50),
   CREATE_USER_CODE     VARCHAR2(50)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50)         not null,
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_T_WIP_WO_EQUIP_RELATION primary key (ID)
);

comment on table T_WIP_WO_EQUIP_RELATION is
'机台生产单的关系表';

comment on column T_WIP_WO_EQUIP_RELATION.ID is
'主键id';

comment on column T_WIP_WO_EQUIP_RELATION.WORK_ORDER_ID is
'生产单外键';

comment on column T_WIP_WO_EQUIP_RELATION.EQUIP_CODE is
'设备编码';

comment on column T_WIP_WO_EQUIP_RELATION.CREATE_USER_CODE is
'创建人';

comment on column T_WIP_WO_EQUIP_RELATION.CREATE_TIME is
'创建时间';

comment on column T_WIP_WO_EQUIP_RELATION.MODIFY_USER_CODE is
'更新人';

comment on column T_WIP_WO_EQUIP_RELATION.MODIFY_TIME is
'更新时间';


-- Create table
create table T_WIP_USER_WORK_HOURS
(
  id                    VARCHAR2(50) not null,
  user_code             VARCHAR2(50),
  user_name             VARCHAR2(50),
  contract_no           VARCHAR2(50),
  operator              VARCHAR2(50),
  work_order_no         VARCHAR2(50),
  equip_code            VARCHAR2(200),
  equip_name            VARCHAR2(200),
  shift_id              VARCHAR2(50),
  shift_name            VARCHAR2(50),
  process_code          VARCHAR2(200),
  process_name          VARCHAR2(200),
  product_type          VARCHAR2(200),
  product_spec          VARCHAR2(200),
  cust_product_type     VARCHAR2(200),
  cust_product_spec     VARCHAR2(200),
  product_work_hours    NUMBER(10,2),
  product_support_hours NUMBER(10,2),
  overtime_hours        NUMBER(10,2),
  support_hours         NUMBER(10,2),
  finished_length       NUMBER,
  coefficient           NUMBER(8,2),
  report_date           VARCHAR2(50),
  report_type           VARCHAR2(50),
  org_code              VARCHAR2(50),
  create_user_code      VARCHAR2(50),
  create_time           TIMESTAMP(6),
  modify_time           TIMESTAMP(6),
  modify_user_code      VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table T_WIP_USER_WORK_HOURS
  is '员工定额记录报表';
-- Add comments to the columns 
comment on column T_WIP_USER_WORK_HOURS.id
  is 'ID';
comment on column T_WIP_USER_WORK_HOURS.user_code
  is '员工工号';
comment on column T_WIP_USER_WORK_HOURS.user_name
  is '员工名称';
comment on column T_WIP_USER_WORK_HOURS.contract_no
  is '订单号/合同号';
comment on column T_WIP_USER_WORK_HOURS.operator
  is '经办人';
comment on column T_WIP_USER_WORK_HOURS.work_order_no
  is '生产单号';
comment on column T_WIP_USER_WORK_HOURS.equip_code
  is '设备编码';
comment on column T_WIP_USER_WORK_HOURS.equip_name
  is '设备名称';
comment on column T_WIP_USER_WORK_HOURS.shift_id
  is '班次';
comment on column T_WIP_USER_WORK_HOURS.shift_name
  is '班次名称';
comment on column T_WIP_USER_WORK_HOURS.process_code
  is '工序编码';
comment on column T_WIP_USER_WORK_HOURS.process_name
  is '工序名称'; 
comment on column T_WIP_USER_WORK_HOURS.product_type
  is '产品型号';
comment on column T_WIP_USER_WORK_HOURS.product_spec
  is '产品规格';
comment on column T_WIP_USER_WORK_HOURS.cust_product_type
  is '客户产品型号';
comment on column T_WIP_USER_WORK_HOURS.cust_product_spec
  is '客户产品规格';
comment on column T_WIP_USER_WORK_HOURS.product_work_hours
  is '生产工时';
comment on column T_WIP_USER_WORK_HOURS.product_support_hours
  is '生产辅助工时';
comment on column T_WIP_USER_WORK_HOURS.overtime_hours
  is '加班工时';
comment on column T_WIP_USER_WORK_HOURS.support_hours
  is '辅助工时';
comment on column T_WIP_USER_WORK_HOURS.finished_length
  is '长度';
comment on column T_WIP_USER_WORK_HOURS.coefficient
  is '定额系数';
comment on column T_WIP_USER_WORK_HOURS.report_date
  is '查询日期';
comment on column T_WIP_USER_WORK_HOURS.report_type
  is '报表类型';
comment on column T_WIP_USER_WORK_HOURS.org_code
  is '组织机构';
comment on column T_WIP_USER_WORK_HOURS.create_user_code
  is '创建人';
comment on column T_WIP_USER_WORK_HOURS.create_time
  is '创建时间';
comment on column T_WIP_USER_WORK_HOURS.modify_time
  is '修改时间';
comment on column T_WIP_USER_WORK_HOURS.modify_user_code
  is '修改人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WIP_USER_WORK_HOURS
  add constraint PK_T_WIP_USER_WORK_HOURS primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
  
  
  
-- Create table
create table T_WIP_TURNOVER_REPORT
(
  id                VARCHAR2(50) not null,
  equip_code        VARCHAR2(50),
  shift_name        VARCHAR2(50),
  db_user_code      VARCHAR2(20),
  db_user_name      VARCHAR2(50),
  fdb_user_code     VARCHAR2(20),
  fdb_user_name     VARCHAR2(50),
  fzg_user_code     VARCHAR2(20),
  fzg_user_name     VARCHAR2(50),
  work_order_no     VARCHAR2(50),
  contract_no       VARCHAR2(50),
  cust_type         VARCHAR2(200),
  cust_spec         VARCHAR2(200),
  work_order_length NUMBER(10,2),
  report_length     NUMBER(10,2),
  create_user_code  VARCHAR2(50),
  create_time       TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the columns 
comment on column T_WIP_TURNOVER_REPORT.id
  is '主键';
comment on column T_WIP_TURNOVER_REPORT.equip_code
  is '设备编号';
comment on column T_WIP_TURNOVER_REPORT.shift_name
  is '版次';
comment on column T_WIP_TURNOVER_REPORT.db_user_code
  is '挡班工号';
comment on column T_WIP_TURNOVER_REPORT.db_user_name
  is '挡班姓名';
comment on column T_WIP_TURNOVER_REPORT.fdb_user_code
  is '副挡班工号';
comment on column T_WIP_TURNOVER_REPORT.fdb_user_name
  is '副挡班姓名';
comment on column T_WIP_TURNOVER_REPORT.fzg_user_code
  is '辅助工工号';
comment on column T_WIP_TURNOVER_REPORT.fzg_user_name
  is '辅助工姓名';
comment on column T_WIP_TURNOVER_REPORT.work_order_no
  is '生产单号';
comment on column T_WIP_TURNOVER_REPORT.contract_no
  is '合同号';
comment on column T_WIP_TURNOVER_REPORT.cust_type
  is '客户型号';
comment on column T_WIP_TURNOVER_REPORT.cust_spec
  is '规格';
comment on column T_WIP_TURNOVER_REPORT.work_order_length
  is '投产长度';
comment on column T_WIP_TURNOVER_REPORT.report_length
  is '报工长度';
comment on column T_WIP_TURNOVER_REPORT.create_user_code
  is '创人人员code';
comment on column T_WIP_TURNOVER_REPORT.create_time
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WIP_TURNOVER_REPORT
  add constraint PK_TURNOVER_REPORT primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

-- Create table
create table T_WIP_TURNOVER_MAT_DETAIL
(
  id              VARCHAR2(50) not null,
  mat_code        VARCHAR2(100),
  mat_name        VARCHAR2(200),
  quota_quantity  NUMBER(10,2),
  real_quantity   NUMBER(10,2),
  turnover_rpt_id VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table T_WIP_TURNOVER_MAT_DETAIL
  is '交班报表物料详情';
-- Add comments to the columns 
comment on column T_WIP_TURNOVER_MAT_DETAIL.id
  is '主键';
comment on column T_WIP_TURNOVER_MAT_DETAIL.mat_code
  is '物料代码';
comment on column T_WIP_TURNOVER_MAT_DETAIL.mat_name
  is '物料名称';
comment on column T_WIP_TURNOVER_MAT_DETAIL.quota_quantity
  is '定额用量';
comment on column T_WIP_TURNOVER_MAT_DETAIL.real_quantity
  is '实际用量';
comment on column T_WIP_TURNOVER_MAT_DETAIL.turnover_rpt_id
  is '交班报表外键';
-- Create/Recreate indexes 
create index IDX_TURNOVER_RPT_ID on T_WIP_TURNOVER_MAT_DETAIL (TURNOVER_RPT_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WIP_TURNOVER_MAT_DETAIL
  add constraint PK_TURNOVER_DT primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table T_WIP_TURNOVER_MAT_DETAIL
  add constraint FK_TURNOVER_RPT_ID foreign key (TURNOVER_RPT_ID)
  references T_WIP_TURNOVER_REPORT (ID);

-- Create table
create table T_PLA_CU_ORDER_ITEM_PRO_DEC_OA
(
  id                   VARCHAR2(50) not null,
  order_item_dec_id    VARCHAR2(50) not null,
  crafts_id            VARCHAR2(50),
  process_path         VARCHAR2(4000),
  process_name         VARCHAR2(200),
  half_product_code    VARCHAR2(50),
  contract_no          VARCHAR2(50),
  process_code         VARCHAR2(50),
  process_id           VARCHAR2(50),
  equip_code           VARCHAR2(50),
  plan_work_hours      NUMBER(6,2),
  use_stock            CHAR(1) default '0' not null,
  used_stock_length    NUMBER(10,2),
  product_spec         VARCHAR2(200),
  product_code         VARCHAR2(50),
  earliest_start_date  TIMESTAMP(6),
  earliest_finish_date TIMESTAMP(6),
  latest_start_date    TIMESTAMP(6),
  latest_finish_date   TIMESTAMP(6),
  un_finished_length   NUMBER(10,2),
  finished_length      NUMBER(10,2),
  status               VARCHAR2(50),
  create_user_code     VARCHAR2(50),
  create_time          TIMESTAMP(6) not null,
  modify_user_code     VARCHAR2(50),
  modify_time          TIMESTAMP(6) not null,
  org_code             VARCHAR2(50),
  optional_equip_code  VARCHAR2(500),
  is_locked            CHAR(1) default '0',
  next_order_id        VARCHAR2(50),
  fixed_equip_code     VARCHAR2(500)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6M
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table T_PLA_CU_ORDER_ITEM_PRO_DEC_OA
  is '用作计算OA的时候使用，不用每次都分解改数据，只在工艺变化或者是新订单的时候分解该数据';
-- Add comments to the columns 
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.id
  is 'ID';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.order_item_dec_id
  is '订单明细分解ID';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.crafts_id
  is '工艺ID';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.process_path
  is '工序路径';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.process_name
  is '工序名称';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.half_product_code
  is '半成品代码';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.contract_no
  is '合同号';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.process_code
  is '工序CODE';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.process_id
  is '工序ID';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.equip_code
  is '加工设备';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.plan_work_hours
  is '计划用时';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.use_stock
  is '是否使用半成品库存';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.used_stock_length
  is '库存使用量';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.product_spec
  is '产品规格';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.product_code
  is '产品编码';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.earliest_start_date
  is '工序最早开始时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.earliest_finish_date
  is '工序最早结束时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.latest_start_date
  is '工序最晚开始时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.latest_finish_date
  is '工序最晚结束时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.un_finished_length
  is '未生产量';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.finished_length
  is '已生产量';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.status
  is '状态';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.create_user_code
  is '创建人';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.create_time
  is '创建时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.modify_user_code
  is '修改人';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.modify_time
  is '修改时间';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.org_code
  is '数据所属组织';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.optional_equip_code
  is '可选设备';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.is_locked
  is '是否锁定';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.next_order_id
  is '下道工序的工单id';
comment on column T_PLA_CU_ORDER_ITEM_PRO_DEC_OA.fixed_equip_code
  is '固定加工设备';

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PLA_CU_ORDER_ITEM_PRO_DEC_OA
  add constraint PK_CU_OR_ITEM_PRO_DEC_OA_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 704K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table T_PLA_CU_ORDER_ITEM_PRO_DEC_OA
  add constraint FK_CU_OR_ITEM_PRO_DEC_OA_ID foreign key (ORDER_ITEM_DEC_ID)
  references T_PLA_CUSTOMER_ORDER_ITEM_DEC (ID);

  

/*==============================================================*/
/* Table: t_wip_wo_cusOrder_relation                                  */
/*==============================================================*/
create table t_wip_wo_cusOrder_relation 
(
   ID                   VARCHAR2(50)         not null,
   work_order_id        VARCHAR2(50)  not null,
   CUS_ORDER_ITEM_ID  VARCHAR2(50)         not null,
   WORK_ORDER_NO         VARCHAR2(50)  not null,
   PROCESS_CODE         VARCHAR2(50) ,
   SPLIT_LENGTH_ROLE    VARCHAR2(100),
   CREATE_USER_CODE     VARCHAR2(50),
   CREATE_TIME          TIMESTAMP            not null,
   MODIFY_USER_CODE     VARCHAR2(50),
   MODIFY_TIME          TIMESTAMP            not null,
   constraint PK_wip_wo_cusOrder_relation_ID primary key (ID)
);

comment on table t_wip_wo_cusOrder_relation is
'生产单和订单产品关系';

comment on column t_wip_wo_cusOrder_relation.ID is
'ID';

comment on column t_wip_wo_cusOrder_relation.work_order_id is
'生产单ID';

comment on column t_wip_wo_cusOrder_relation.CUS_ORDER_ITEM_ID is
'订单产品ID';

comment on column t_wip_wo_cusOrder_relation.WORK_ORDER_NO is
'生产单号';

comment on column t_wip_wo_cusOrder_relation.PROCESS_CODE is
'工序编码';

comment on column t_wip_wo_cusOrder_relation.SPLIT_LENGTH_ROLE is
'分段要求：1500+1000*2+500*3';

comment on column t_wip_wo_cusOrder_relation.CREATE_USER_CODE is
'创建人';

comment on column t_wip_wo_cusOrder_relation.CREATE_TIME is
'创建时间';

comment on column t_wip_wo_cusOrder_relation.MODIFY_USER_CODE is
'修改人';

comment on column t_wip_wo_cusOrder_relation.MODIFY_TIME is
'修改时间';

-- Create table
create table T_PLA_OA_UPTEMP
(
  id               VARCHAR2(50) not null,
  table_name       VARCHAR2(50),
  table_uid        VARCHAR2(50),
  plan_start_date  TIMESTAMP(6),
  plan_finish_date TIMESTAMP(6),
  last_oa          TIMESTAMP(6),
  org_code         VARCHAR2(50),
  create_user_code VARCHAR2(50),
  create_time      TIMESTAMP(6),
  modify_user_code VARCHAR2(50),
  modify_time      TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table T_PLA_OA_UPTEMP
  is '计算OA最后更新数据的中间临时表';
-- Add comments to the columns 
comment on column T_PLA_OA_UPTEMP.id
  is '主键';
comment on column T_PLA_OA_UPTEMP.table_name
  is '更新表名';
comment on column T_PLA_OA_UPTEMP.table_uid
  is '更新表ID';
comment on column T_PLA_OA_UPTEMP.plan_start_date
  is '计划开工日期';
comment on column T_PLA_OA_UPTEMP.plan_finish_date
  is '计划完成日期';
comment on column T_PLA_OA_UPTEMP.last_oa
  is '上次计算日期';
comment on column T_PLA_OA_UPTEMP.org_code
  is '组织机构';
comment on column T_PLA_OA_UPTEMP.create_user_code
  is '创建人';
comment on column T_PLA_OA_UPTEMP.create_time
  is '创建时间';
comment on column T_PLA_OA_UPTEMP.modify_user_code
  is '修改人';
comment on column T_PLA_OA_UPTEMP.modify_time
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PLA_OA_UPTEMP
  add constraint T_PLA_OA_UPTEMP_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
