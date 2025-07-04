CREATE TABLE casa_account
(
    account_no   VARCHAR(20) PRIMARY KEY NOT NULL,
    account_type VARCHAR(20),
    amount       DECIMAL(14, 4),
    status       VARCHAR(20),
    user_id      BIGINT                  NOT NULL
);

create index idx_casa_acc_type
    on casa_account (user_id, account_type);