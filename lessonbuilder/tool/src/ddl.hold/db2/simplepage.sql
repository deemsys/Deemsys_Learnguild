
    create table SimplePageTool (
        id bigint generated by default as identity,
        pageId varchar(250),
        sequence integer,
        sakaiId varchar(250),
        primary key (id)
    );