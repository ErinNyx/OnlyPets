CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(75) UNIQUE,
  `email` varchar(255) UNIQUE,
  `password` text,
  `role` text,
  `timedout` bigint DEFAULT 0,
  `banned` boolean DEFAULT false,
  `is_email_verified` boolean,
  `code` text
);

CREATE TABLE `settings` (
  `id` int PRIMARY KEY,
  `avatar` text COMMENT 'href to files assets/avatars'
);

CREATE TABLE `posts` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `author` text,
  `reports` int DEFAULT 0,
  `title` varchar(20),
  `picture` text COMMENT 'href to files assets/pets',
  `created_at` int,
  `flagged` boolean DEFAULT false
);

CREATE TABLE `reports` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `reported_by` int,
  `post` int
);

CREATE TABLE `user_likes` (
  `user` int PRIMARY KEY,
  `post` int
);

CREATE TABLE `user_dislikes` (
  `user` int PRIMARY KEY,
  `post` int
);
