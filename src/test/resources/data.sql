
INSERT INTO departments (id, name) VALUES (1, 'HR');
INSERT INTO departments (id, name) VALUES (2, 'Finance');
INSERT INTO departments (id, name) VALUES (3, 'IT');


INSERT INTO employees (first_name, last_name, position, salary, department_id)
VALUES ('John', 'Doe', 'HR Specialist', 50000.0, 1);

INSERT INTO employees (first_name, last_name, position, salary, department_id)
VALUES ('Jane', 'Smith', 'Financial Analyst', 60000.0, 2);

INSERT INTO employees (first_name, last_name, position, salary, department_id)
VALUES ('Alice', 'Johnson', 'Software Engineer', 70000.0, 3);

INSERT INTO employees (first_name, last_name, position, salary, department_id)
VALUES ('Mike', 'Brown', 'Project Manager', 80000.0, 3);
