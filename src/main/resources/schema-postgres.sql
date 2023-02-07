create table if not exists hashtag
(
    id        bigserial
        primary key,
    name      varchar(255) not null,
    parent_id bigint
        constraint fkipq4ynnrnhdmnf765fv7e39s2
            references hashtag
);

alter table hashtag
    owner to postgres;

create table if not exists member
(
    id            bigserial
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    birthyear     smallint     not null,
    email         varchar(255) not null,
    gender        varchar(255) not null,
    is_deleted    boolean default false,
    nickname      varchar(255),
    refresh_token varchar(255),
    role          varchar(255) not null,
    social        varchar(255) not null,
    social_id     varchar(255) not null
);

alter table member
    owner to postgres;

create table if not exists post
(
    id            bigserial
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    description   varchar(255),
    dislike_count bigint       not null,
    is_deleted    boolean default false,
    like_count    bigint       not null,
    title         varchar(255) not null,
    member_id     bigint       not null
        constraint fk83s99f4kx8oiqm3ro0sasmpww
            references member
);

alter table post
    owner to postgres;

create table if not exists comment
(
    id         bigserial
        primary key,
    created_at timestamp,
    updated_at timestamp,
    comment    varchar(255) not null,
    member_id  bigint       not null
        constraint fkmrrrpi513ssu63i2783jyiv9m
            references member,
    post_id    bigint       not null
        constraint fks1slvnkuemjsq2kj4h3vhx7i1
            references post
);

alter table comment
    owner to postgres;

create table if not exists evaluate
(
    id            bigserial
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    evaluate_enum varchar(255) not null,
    member_id     bigint       not null
        constraint fkgof2vd111650jobq00g2ntpt3
            references member,
    post_id       bigint       not null
        constraint fk13pf2vuqe0qr0c3dc62k6vsa7
            references post
);

alter table evaluate
    owner to postgres;

create table if not exists hashtag_post
(
    id         bigserial
        primary key,
    hashtag_id bigint not null
        constraint fk27xov1e67k9vnnvfojnfk8vkc
            references hashtag,
    post_id    bigint not null
        constraint fkhxtkd48remreae69yduo7kxvq
            references post
);

alter table hashtag_post
    owner to postgres;

create table if not exists img
(
    id            bigserial
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    is_deleted    boolean default false,
    name          varchar(255) not null,
    original_name varchar(255) not null,
    url           varchar(500),
    post_id       bigint       not null
        constraint fkpecunieot96r3r09a7bsn9aj0
            references post
);

alter table img
    owner to postgres;