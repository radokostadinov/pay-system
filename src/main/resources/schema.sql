CREATE DATABASE PAYMENTS;
USE PAYMENTS;

CREATE TABLE transaction (
                             `uuid` varchar(255) not null,
                             `amount` decimal(13,2) not null,
                             `status` varchar(10) not null,
                             `reference_id` integer,
                             `merchant_id` integer,
                             `client_id` integer,
                             foreign key (`merchant_id`)
                                 references merchant (`id`),
                             foreign key (`client_id`)
                                 references client (`id`),
                             foreign key (`uuid`)
                                 references products (`id`) 	);

CREATE TABLE merchant (
                          `id` integer not null auto_increment,
                          `description` varchar(255) not null,
                          `email` varchar(255) not null,
                          `status` varchar(10) not null,
                          `total_transaction_sum` decimal(13.2) not null,
                          `customer_phone` varchar(255) not null,
                          primary key (`id`) );

CREATE TABLE products (
                          `id` integer not null auto_increment,
                          `price` decimal(13.2) not null,
                          `merchant_id` integer,
                          foreign key (`merchant_id`)
                              references merchant (`id`) );

CREATE TABLE client (
                        `id` integer not null auto_increment,
                        `customer_email` varchar(255) not null,
                        `customer_phone` varchar(255) not null,
                        `total_money` decimal(13.2),
                        primary key (`id`)	);
