create table user_subscriptions (
    subscriber_id int8 not null references usr,
    channel_id int8 not null references tests,
    primary key (subscriber_id, channel_id)
);

create table task_subscriptions (
    subscriber_id int8 not null references tasks,
    channel_id int8 not null references tests,
    primary key (subscriber_id, channel_id)
);

create table user_results (
    user_id int8 not null references usr,
    task_id int8 not null references tasks,
    result_id int8 not null references results,
    primary key (user_id, task_id, result_id)
)