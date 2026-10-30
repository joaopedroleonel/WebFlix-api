-- Schema do WebFlix.
-- Executado pela imagem oficial do MySQL apenas quando o volume de dados está vazio.

CREATE TABLE IF NOT EXISTS `Genres` (
    `genreID` INT NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(50) NOT NULL,
    CONSTRAINT `PK_Genres` PRIMARY KEY (`genreID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Videos` (
    `videoID`     INT NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(80)  NOT NULL,
    `description` VARCHAR(250) NULL,
    `imageUrl`    VARCHAR(250) NOT NULL,
    `videoUrl`    VARCHAR(250) NOT NULL,
    CONSTRAINT `PK_Videos` PRIMARY KEY (`videoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `GenreVideo` (
    `GenresgenreID` INT NOT NULL,
    `VideosvideoID` INT NOT NULL,
    CONSTRAINT `PK_GenreVideo` PRIMARY KEY (`GenresgenreID`, `VideosvideoID`),
    INDEX `IX_GenreVideo_VideosvideoID` (`VideosvideoID`),
    CONSTRAINT `FK_GenreVideo_Genres_GenresgenreID` FOREIGN KEY (`GenresgenreID`)
        REFERENCES `Genres` (`genreID`) ON DELETE CASCADE,
    CONSTRAINT `FK_GenreVideo_Videos_VideosvideoID` FOREIGN KEY (`VideosvideoID`)
        REFERENCES `Videos` (`videoID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
