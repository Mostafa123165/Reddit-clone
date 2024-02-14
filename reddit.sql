DROP DATABASE IF EXISTS redditDB;

CREATE DATABASE IF NOT EXISTS redditDB ;

use redditDB ;

DROP TABLE IF EXISTS `vote` ;
DROP TABLE IF EXISTS `post` ;
DROP TABLE IF EXISTS `subreddit` ;
DROP TABLE IF EXISTS `user` ;
DROP TABLE IF EXISTS `token` ;


CREATE TABLE IF NOT EXISTS `user`(
	`id` 		 BIGINT NOT NULL AUTO_INCREMENT ,
    `user_name`  VARCHAR(55) ,
    `password`	 VARCHAR(255) ,
    `email`	     VARCHAR(100) ,
    `created_at` DATETIME ,	
	`enabled`    BOOLEAN DEFAULT FALSE,
    UNIQUE KEY (`user_name`) ,
    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `token`(
	`id` 			BIGINT NOT NULL AUTO_INCREMENT ,
	`token` 		VARCHAR(255) ,
    `expiry_date` 	DATETIME ,
	`user_id` 	BIGINT ,
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ,
	PRIMARY KEY (`id`) 
);




CREATE TABLE IF NOT EXISTS `subreddit`(
	`id` BIGINT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(55) ,
    `description` VARCHAR(255) ,
	`user_id` BIGINT DEFAULT NULL ,
    `created_at` DATETIME ,

    PRIMARY KEY (`id`) ,
    FOREIGN KEY (`user_id`)	 REFERENCES `user` (`id`) 
); 

CREATE TABLE IF NOT EXISTS `post`(
	`id` 			 BIGINT NOT NULL AUTO_INCREMENT ,
    `post_name` 	 VARCHAR(55) ,
    `url`			 VARCHAR(255) ,
    `description` 	 TEXT ,
    `voteCount`		 INT DEFAULT 0 ,
	`created_at`		 DaTETIME,
    `user_id`		 BIGINT DEFAULT NULL ,
	`subreddit_id`	 BIGINT DEFAULT NULL ,

    PRIMARY KEY (`id`) ,
    FOREIGN KEY (`user_id`)	     REFERENCES `user` (`id`) ,
	FOREIGN KEY (`subreddit_id`) REFERENCES `subreddit` (`id`) 		
    
);

CREATE TABLE IF NOT EXISTS `comment`(
	`id` 		BIGINT NOT NULL AUTO_INCREMENT ,
    `text` 		VARCHAR(255) ,
    `user_id` 	BIGINT DEFAULT NULL ,
	`post_id` 	BIGINT DEFAULT NULL ,

    PRIMARY KEY (`id`) ,
    FOREIGN KEY (`user_id`)	REFERENCES `user` (`id`) ,
	FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) 		
    
);

CREATE TABLE `vote` (
    `id` 			BIGINT AUTO_INCREMENT,
    `vote_type` 	ENUM ('UPVOTE','DOWNVOTE') ,
    `post_id` 		BIGINT,
    `user_id` 		BIGINT,
    FOREIGN KEY (`post_id`) REFERENCES `post`(`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	PRIMARY KEY (`id`) 
);
