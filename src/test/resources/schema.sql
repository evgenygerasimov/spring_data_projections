CREATE TABLE IF NOT EXISTS departments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           position VARCHAR(255),
                           salary DOUBLE,
                           department_id BIGINT NOT NULL,
                           FOREIGN KEY (department_id) REFERENCES departments(id)
);
