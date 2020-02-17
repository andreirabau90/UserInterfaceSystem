create table if not exists user_account
(
  id              bigserial not null
  constraint user_account_pkey
  primary key,
  create_date     date,
  user_first_name varchar(16),
  user_last_name  varchar(16),
  user_pass       varchar(255),
  user_role       varchar(255),
  user_status     varchar(255),
  user_name       varchar(16)
);

