create table products
(
    id         bigserial     not null,
    code       varchar(255)  not null,
    name       varchar(255)  not null,
    price      numeric(5, 2) not null,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    primary key (id),
    constraint product_code_unique unique (code)
);

insert into products(code, name, price)
values ('P101', 'product-1', 24.50),
       ('P102', 'product-2', 34.50),
       ('P103', 'product-3', -14.50)
;