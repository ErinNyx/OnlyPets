# OnlyPets

FOR ANDY AND DUNCAN
Some changes to the DB
- Removed settings.is_moderator
- Removed settings.is_admin
- Changed settings.timedout and Posts.created_at to int
+ Added users.role
At the very least, the basics should be in place. You can register an account and login. Giving the wrong credentials on login will not crash the server however it's also not handled correctly. I'll be doing basic housekeeping for that as well tomorrow.
   
 Thank you for being patient. Here's the updated SQL code. DB should be created as "onlypets"
 ```SQL
 CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(75) UNIQUE,
  `email` varchar(255) UNIQUE,
  `password` text,
  `role` text DEFAULT "USER"
);

CREATE TABLE `settings` (
  `id` int PRIMARY KEY,
  `avatar` text COMMENT 'href to files assets/avatars',
  `timedout` int DEFAULT 0,
  `banned` boolean DEFAULT false
);

CREATE TABLE `posts` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `likes` int,
  `dislikes` int,
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
