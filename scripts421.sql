alter table student
    add constraint age_constrain check ( age >= 16 );    -- добавляем ограничение по возрасту
alter table student
    alter column name set not null, -- ограничения на null-значения в колонке name
    add constraint name_unique unique (name);   -- ограничения на уникальность значения
alter table faculty
    add constraint name_color_unique unique (name, color);  -- уникальность пар значений
alter table student
    alter column age set default 20;    -- установка значения по умолчанию

