-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2025 at 01:50 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `handyman_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `street` varchar(255) NOT NULL,
  `barangay` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `country` varchar(255) DEFAULT 'Philippines',
  `region` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` (`id`, `user_id`, `address_type`, `street`, `barangay`, `city`, `province`, `postal_code`, `country`, `region`) VALUES
(17, 1, 'Home', 'Manokan St.', 'Guihing', 'Hagonoy', 'Davao del Sur', '8001', 'Philippines', 'Region XI - Davao Region'),
(18, 1, 'Home', 'Manokan St.', 'Guihing', 'Hagonoy', 'Davao del Sur', '8001', 'Philippines', 'Region XI - Davao Region'),
(19, 1, 'Home', 'Sampaguita st.', 'Lucenta', 'Katipunan', 'Aklan', '6564', 'Philippines', 'Region IV-A - CALABARZON'),
(20, 1, 'Home', 'Rho st.', 'Alponce', 'Jornadal', 'Bulacan', '7850', 'Philippines', 'Region IV-A - CALABARZON'),
(21, 6, 'Home', 'Sandawa Phase 2', 'Matina', 'Davao', 'Davao del Sur', '8000', 'Philippines', 'Region XI - Davao Region'),
(22, 10, 'Home', 'Blk 3 lot 5 amparo homes', 'ilang ', 'Davao city', 'Davao del Sur', '8000', 'Philippines', 'Region XI - Davao Region'),
(23, 14, 'Work', 'EUGANICE ST.', 'P.E', 'CITY BOBO', 'Davao del Sur', '8000', 'Philippines', 'Region XI - Davao Region'),
(24, 15, 'Work', 'bonifacio st.', 'Laowens', 'Jay III City', 'Davao del Sur', '8000', 'Philippines', 'Region XI - Davao Region'),
(25, 1, 'Home', 'fo[emf ewkfkmweflkneflkwef', 'reknnrfkrgopnernoperrrrrrrrrrref', 'erfknerml;ergk;n', 'Agusan del Sur', '90ew99090', 'Philippines', 'Region VII - Central Visayas');

-- --------------------------------------------------------

--
-- Table structure for table `booked_service`
--

CREATE TABLE `booked_service` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `job_complexity` varchar(50) NOT NULL,
  `service_fee` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booked_service`
--

INSERT INTO `booked_service` (`id`, `booking_id`, `service_name`, `job_complexity`, `service_fee`) VALUES
(1, 1, 'Custom Dining Table Set', 'low', 500.50),
(2, 1, 'Custom Office Desk', 'low', 320.25),
(3, 1, 'Custom Bed Frames', 'low', 420.75),
(4, 2, 'Custom Dining Table Set', 'low', 500.50),
(5, 2, 'Custom Office Desk', 'low', 320.25),
(6, 2, 'Custom Bed Frames', 'low', 420.75),
(7, 2, 'Custom Bookshelves', 'low', 210.60),
(8, 2, 'Custom Kitchen Cabinets', 'low', 620.85),
(9, 2, 'Table & Chair Repair', 'low', 55.50),
(10, 3, 'Custom Dining Table Set', 'medium', 1100.63),
(11, 3, 'Custom Office Desk', 'low', 320.25),
(12, 3, 'Custom Bed Frames', 'high', 1550.50),
(13, 3, 'Hardwood Floor Installation', 'medium', 1170.00),
(14, 3, 'Patio Paver Installation', 'high', 1800.00),
(15, 4, 'Custom Dining Table Set', 'low', 500.50),
(16, 5, 'Custom Dining Table Set', 'medium', 1100.63),
(17, 5, 'Custom Office Desk', 'high', 1250.99),
(18, 5, 'Custom Bed Frames', 'high', 1550.50);

-- --------------------------------------------------------

--
-- Table structure for table `creditcard`
--

CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `card_name` varchar(255) NOT NULL,
  `card_number` varchar(16) NOT NULL,
  `cvv` varchar(4) NOT NULL,
  `billing_address` varchar(255) DEFAULT NULL,
  `zip_code` varchar(10) DEFAULT NULL,
  `expiry` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `creditcard`
--

INSERT INTO `creditcard` (`id`, `user_id`, `card_name`, `card_number`, `cvv`, `billing_address`, `zip_code`, `expiry`) VALUES
(1, 1, 'land bank', '53456456', '453', 'manokan st. guihing hagonoy davao del sur', '9808', '43/33'),
(4, 15, 'davaolao', '46346346', '456', 'lao st, jaythe third', '8000', '45/09');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role_id` int(11) NOT NULL,
  `status` enum('Available','Unavailable') NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `name`, `role_id`, `status`, `profile_picture`, `description`, `service_id`, `phone_number`) VALUES
(1, 'Alex Taylor', 8, 'Available', 'project/demo/imagesemployee/AlexTaylor.png', 'Alex Taylor can craft custom dining table sets designed to match your home\'s unique style. Alex ensures each piece is both functional and visually appealing. Every table is tailored to bring family and friends together in a space of comfort and elegance.', 1, '0921-123-4567'),
(2, 'Jordan Morgan', 8, 'Available', 'project/demo/imagesemployee/JordanMorgan.png', 'Jordan Morgan designs custom office desks that enhance productivity and comfort. Each desk is built with ergonomic principles and your preferences in mind. Jordan\'s work ensures a workspace that is as functional as it is stylish.', 2, '0917-234-5678'),
(3, 'Casey Lee', 8, 'Unavailable', 'project/demo/imagesemployee/CaseyLee.png', 'Casey Lee specializes in custom bed frames that blend durability and modern design. Whether you need a minimalist frame or a statement piece, Casey has you covered. Each frame is crafted to ensure you sleep in both comfort and style.', 3, '0932-345-6789'),
(4, 'Riley Carter', 8, 'Available', 'project/demo/imagesemployee/RileyCarter.png', 'Riley Carter creates custom bookshelves tailored to fit your space perfectly. Riley\'s designs maximize storage while complementing your interior. Each bookshelf is built with precision and attention to detail, ensuring a flawless fit.', 4, '0915-456-7890'),
(5, 'Jamie Brooks', 8, 'Unavailable', 'project/demo/imagesemployee/JamieBrooks.png', 'Jamie Brooks builds custom kitchen cabinets that optimize storage and style. Jamie combines modern aesthetics with practical layouts to suit your needs. These cabinets are designed to transform your kitchen into an organized and elegant space.', 5, '0926-567-8901'),
(6, 'Morgan Kelly', 8, 'Available', 'project/demo/imagesemployee/MorganKelly.png', 'Morgan Kelly handles table and chair repairs, restoring your furniture to its original glory. Morgan ensures every piece is sturdy and functional. From fixing scratches to tightening joints, your furniture will feel brand new.', 6, '0933-678-9012'),
(7, 'Taylor Jordan', 8, 'Available', 'project/demo/imagesemployee/TaylorJordan.png', 'Taylor Jordan restores wooden furniture with expert refinishing techniques. Taylor breathes new life into old pieces, enhancing their natural beauty. Each refinished item is polished to perfection, creating timeless elegance.', 7, '0945-789-0123'),
(8, 'Avery Parker', 8, 'Available', 'project/demo/imagesemployee/AveryParker.png', 'Avery Parker fixes cabinet doors, repairing hinges, knobs, and structural issues. Avery\'s attention to detail ensures smooth functionality and a like-new appearance. Your cabinets will look and work perfectly after their expert touch.', 8, '0950-890-1234'),
(9, 'Logan Quinn', 8, 'Unavailable', 'project/demo/imagesemployee/LoganQuinn.png', 'Logan Quinn specializes in upholstered furniture repairs, replacing fabrics and fixing frames. Logan can rejuvenate worn-out sofas and chairs, making them stylish and comfortable again. With Logan\'s skills, your furniture will stand out for years to come.', 9, '0912-901-2345'),
(10, 'Quinn Harper', 8, 'Available', 'project/demo/imagesemployee/QuinnHarper.png', 'Quinn Harper restores antique furniture, preserving its vintage charm. Quinn meticulously repairs and refinishes each piece to maintain its authenticity. These treasures are given new life while retaining their historical value.', 10, '0927-012-3456'),
(11, 'Skyler Hayes', 8, 'Available', 'project/demo/imagesemployee/SkylerHayes.png', 'Skyler Hayes installs door and window frames with precision and durability in mind. Skyler ensures proper alignment and a secure fit. Every installation blends functionality with a polished appearance.', 22, '0919-123-4567'),
(12, 'Rowan Elliott', 8, 'Unavailable', 'project/demo/imagesemployee/RowanElliott.png', 'Rowan Elliott sets up cabinets and shelves, creating custom storage solutions. Rowan designs each unit for practicality and aesthetic appeal. Whether it\'s for your home or office, you\'ll have storage that fits perfectly.', 23, '0920-234-5678'),
(13, 'Emerson Gray', 8, 'Available', 'project/demo/imagesemployee/EmersonGray.png', 'Emerson Gray delivers wooden flooring installations that add warmth and elegance to your space. Emerson carefully selects and installs durable materials that stand the test of time. The results are floors that are both functional and visually stunning.', 15, '0936-345-6789'),
(14, 'Dakota Miller', 8, 'Available', 'project/demo/imagesemployee/DakotaMiller.png', 'Dakota Miller builds partition walls to create efficient and private spaces. Dakota ensures each wall is sturdy and blends seamlessly into your home or office. This service is perfect for transforming open areas into functional rooms.', 11, '0917-456-7890'),
(15, 'Sawyer Blake', 8, 'Available', 'project/demo/imagesemployee/SawyerBlake.png', 'Sawyer Blake installs custom staircases, combining safety and style. Sawyer\'s work includes attention to detail, ensuring durability and aesthetic appeal. Each staircase becomes a centerpiece in any home.', 24, '0923-567-8901'),
(16, 'Payton Bennett', 8, 'Available', 'project/demo/imagesemployee/PaytonBennett.png', 'Payton Bennett creates custom wall paneling for decorative or functional use. Payton tailors each panel to match your home\'s interior. These panels add depth and sophistication to your walls.', 21, '0942-678-9012'),
(17, 'Harper Logan', 8, 'Unavailable', 'project/demo/imagesemployee/HarperLogan.png', 'Harper Logan specializes in decorative wood carvings to add artistic touches to your space. Harper\'s detailed craftsmanship brings life to every carving. These pieces are perfect for enhancing the character of any home.', 20, '0951-789-0123'),
(18, 'Cameron Reese', 8, 'Available', 'project/demo/imagesemployee/CameronReese.png', 'Cameron Reese installs wooden ceiling beams to give your space a rustic or modern vibe. Cameron ensures precise installation for both aesthetics and functionality.', 14, '0970-890-1234'),
(19, 'Drew Sutton', 8, 'Available', 'project/demo/imagesemployee/DrewSutton.png', 'Drew Sutton crafts custom outdoor furniture, designed to endure the elements. Drew combines practicality with style, creating pieces that enhance outdoor spaces. Whether it\'s chairs, tables, or benches, you\'ll enjoy both comfort and durability.', 18, '0915-901-2345'),
(20, 'Finley Ross', 8, 'Available', 'project/demo/imagesemployee/FinleyRoss.png', 'Finley Ross designs custom wooden signage, ideal for homes or businesses. Finley\'s signs are both stylish and practical, tailored to your preferences. These unique pieces add a personal touch to any space.', 25, '0930-012-3456'),
(21, 'Lane Avery', 8, 'Unavailable', 'project/demo/imagesemployee/LaneAvery.png', 'Lane Avery builds or repairs wooden decks, creating outdoor spaces perfect for relaxation. Lane ensures each deck is durable, safe, and visually appealing. With Lane\'s expertise, your backyard becomes a beautiful extension of your home.', 17, '0928-345-6789'),
(22, 'Shawn Ellis', 8, 'Available', 'project/demo/imagesemployee/ShawnEllis.png', 'Shawn Ellis installs wooden pergolas, perfect for shaded outdoor lounging or gardens. Shawn focuses on combining function with elegance. These pergolas enhance the charm of any outdoor space.', 12, '0913-456-7890'),
(23, 'Tanner Lane', 8, 'Available', 'project/demo/imagesemployee/TannerLane.png', 'Tanner Lane specializes in fence installations or repairs, ensuring safety and style. Tanner uses durable materials to provide long-lasting fences. Your property will look complete and secure with Tanner\'s touch.', 16, '0943-567-8901'),
(24, 'Reese Monroe', 8, 'Available', 'project/demo/imagesemployee/ReeseMonroe.png', 'Reese Monroe designs and builds gazebos, adding beauty and utility to your garden. Reese creates structures that are both durable and visually appealing. Each gazebo becomes a peaceful retreat in your backyard.', 13, '0918-678-9012'),
(25, 'Parker Chase', 8, 'Available', 'project/demo/imagesemployee/ParkerChase.png', 'Parker Chase creates wooden outdoor storage units, perfect for organizing outdoor essentials. Parker\'s designs are weatherproof and space-efficient. These units are both practical and stylish, enhancing your outdoor space.', 19, '0925-789-0123'),
(26, 'Nathaniel Pierce', 1, 'Unavailable', 'project/demo/imagesemployee/NathanielPierce.png', 'Nathaniel Pierce provides water pipe installation for reliable plumbing systems in new properties. Nathaniel ensures every pipe is durable and precisely installed for long-lasting functionality.', 26, '0931-890-1234'),
(27, 'Isabella Ramirez', 1, 'Unavailable', 'project/demo/imagesemployee/IsabellaRamirez.png', 'Isabella Ramirez specializes in faucet and sink installation, delivering a perfect combination of function and design. Her installations enhance the aesthetics and practicality of your home.', 27, '0952-901-2345'),
(28, 'Dominic Sullivan', 1, 'Available', 'project/demo/imagesemployee/DominicSullivan.png', 'Dominic Sullivan installs toilets, focusing on efficient, water-saving systems. Enjoy modern and reliable bathroom fixtures with Dominic\'s service.', 28, '0968-012-3456'),
(29, 'Evelyn Barrett', 1, 'Available', 'project/demo/imagesemployee/EvelynBarrett.png', 'Evelyn Barrett offers shower system installation, creating luxurious experiences in every bathroom. She customizes options to suit your preferences.', 29, '0914-123-4567'),
(30, 'Gabriel Chandler', 1, 'Available', 'project/demo/imagesemployee/GabrielChandler.png', 'Gabriel Chandler handles water heater installations, ensuring energy-efficient systems that are reliable and long-lasting.', 30, '0929-234-5678'),
(31, 'Sophia Thornton', 1, 'Available', 'project/demo/imagesemployee/SophiaThornton.png', 'Sophia Thornton resolves pipe leaks, providing effective solutions to avoid water damage. Her expertise guarantees lasting fixes.', 31, '0931-123-4567'),
(32, 'Elijah Whitaker', 1, 'Unavailable', 'project/demo/imagesemployee/ElijahWhitaker.png', 'Elijah Whitaker unclogs drains, addressing blockages in kitchens, bathrooms, and outdoor areas. His service ensures proper drainage.', 32, '0922-234-5678'),
(33, 'Amelia Donovan', 1, 'Available', 'project/demo/imagesemployee/AmeliaDonovan.png', 'Amelia Donovan handles toilet repairs, fixing leaks and flushing issues. She ensures reliable solutions for toilet-related problems.', 33, '0947-345-6789'),
(34, 'Sebastian Cross', 1, 'Available', 'project/demo/imagesemployee/SebastianCross.png', 'Sebastian Cross repairs water heaters, resolving heating problems and ensuring efficient operation.', 34, '0918-456-7890'),
(35, 'Victoria Delaney', 1, 'Available', 'project/demo/imagesemployee/VictoriaDelaney.png', 'Victoria Delaney specializes in faucet repairs, stopping drips and fixing handles. Her work restores convenience.', 35, '0923-567-8901'),
(36, 'Benjamin Archer', 1, 'Unavailable', 'project/demo/imagesemployee/BenjaminArcher.png', 'Benjamin Archer provides drain cleaning services, preventing buildup and ensuring your drains flow freely.', 36, '0952-678-9012'),
(37, 'Charlotte Manning', 1, 'Available', 'project/demo/imagesemployee/CharlotteManning.png', 'Charlotte Manning offers septic tank maintenance, including routine pumping and cleaning.', 37, '0971-789-0123'),
(38, 'Caleb Huxley', 1, 'Available', 'project/demo/imagesemployee/CalebHuxley.png', 'Caleb Huxley handles water heater maintenance, flushing sediment and checking efficiency to save energy.', 38, '0915-890-1234'),
(39, 'Abigail Sinclair', 1, 'Available', 'project/demo/imagesemployee/AbigailSinclair.png', 'Abigail Sinclair provides gutter cleaning services, removing debris to ensure proper water flow.', 39, '0926-901-2345'),
(40, 'Alexander Rivers', 1, 'Available', 'project/demo/imagesemployee/AlexanderRivers.png', 'Alexander Rivers performs pipe inspections using cameras to identify potential issues and leaks.', 40, '0933-012-3456'),
(41, 'Penelope Foster', 1, 'Unavailable', 'project/demo/imagesemployee/PenelopeFoster.png', 'Penelope Foster provides emergency pipe burst repairs, addressing leaks quickly to restore functionality.', 41, '0912-123-4567'),
(42, 'Jonathan Merrick', 1, 'Available', 'project/demo/imagesemployee/JonathanMerrick.png', 'Jonathan Merrick specializes in flooding resolution services, repairing causes of standing water.', 42, '0927-234-5678'),
(43, 'Olivia Caldwell', 1, 'Available', 'project/demo/imagesemployee/OliviaCaldwell.png', 'Olivia Caldwell clears severe drain clogs on short notice, ensuring smooth drainage operation.', 43, '0935-345-6789'),
(44, 'Christopher Hale', 1, 'Unavailable', 'project/demo/imagesemployee/ChristopherHale.png', 'Christopher Hale resolves sewer backups, preventing damage and addressing root causes.', 44, '0940-456-7890'),
(45, 'Lillian Monroe', 1, 'Available', 'project/demo/imagesemployee/LillianMonroe.png', 'Lillian Monroe handles toilet overflow repairs, ensuring cleanliness and reliability.', 45, '0956-567-8901'),
(46, 'Nicholas Mercer', 1, 'Available', 'project/demo/imagesemployee/NicholasMercer.png', 'Nicholas Mercer installs rainwater harvesting systems, helping conserve water efficiently.', 46, '0963-678-9012'),
(47, 'Gabriella Vaughn', 1, 'Available', 'project/demo/imagesemployee/GabriellaVaughn.png', 'Gabriella Vaughn sets up greywater recycling systems, contributing to environmentally friendly homes.', 47, '0978-789-0123'),
(48, 'Lawrence Pembroke', 1, 'Unavailable', 'project/demo/imagesemployee/LawrencePembroke.png', 'Lawrence Pembroke uses hydro-jetting services to clean stubborn blockages effectively.', 48, '0914-890-1234'),
(49, 'Madeline Ellison', 1, 'Available', 'project/demo/imagesemployee/MadelineEllison.png', 'Madeline Ellison offers trenchless pipe replacements, avoiding messy digs and preserving landscapes.', 49, '0929-901-2345'),
(50, 'Theodore Ashton', 1, 'Available', 'project/demo/imagesemployee/TheodoreAshton.png', 'Theodore Ashton installs water filtration systems, ensuring clean and healthy water supplies.', 50, '0936-012-3456'),
(51, 'Harrison Black', 2, 'Available', 'project/demo/imagesemployee/HarrisonBlack.png', 'Harrison Black specializes in interior wall painting, adding fresh colors to your living spaces. His attention to detail guarantees stunning results.', 51, '0947-123-4567'),
(52, 'Eleanor Woodruff', 2, 'Available', 'project/demo/imagesemployee/EleanorWoodruff.png', 'Eleanor Woodruff provides ceiling painting, ensuring clean, even coverage with no drips or mess. Her expertise turns ceilings into a design feature.', 52, '0910-234-5678'),
(53, 'Patrick Whitman', 2, 'Unavailable', 'project/demo/imagesemployee/PatrickWhitman.png', 'Patrick Whitman excels in exterior wall painting, using weather-resistant paints to enhance curb appeal and protect your property.', 53, '0925-345-6789'),
(54, 'Margaret Hawthorne', 2, 'Available', 'project/demo/imagesemployee/MargaretHawthorne.png', 'Margaret Hawthorne focuses on fence and gate painting, using durable coatings to protect and beautify outdoor structures.', 54, '0931-456-7890'),
(55, 'Daniel Carrington', 2, 'Available', 'project/demo/imagesemployee/DanielCarrington.png', 'Daniel Carrington creates accent wall paintings that add bold colors and patterns to make a statement in any room.', 55, '0948-567-8901'),
(56, 'Vivian Langston', 2, 'Available', 'project/demo/imagesemployee/VivianLangston.png', 'Vivian Langston provides office interior painting, creating clean, polished spaces that boost productivity and morale.', 56, '0953-678-9012'),
(57, 'Samuel Winslow', 2, 'Available', 'project/demo/imagesemployee/SamuelWinslow.png', 'Samuel Winslow offers warehouse wall painting, ensuring durable finishes for demanding industrial areas.', 57, '0960-789-0123'),
(58, 'Clara Huntington', 2, 'Available', 'project/demo/imagesemployee/ClaraHuntington.png', 'Clara Huntington applies floor epoxy coatings, delivering durable and easy-to-maintain surfaces for homes and businesses.', 58, '0972-890-1234'),
(59, 'Oliver Greystone', 2, 'Unavailable', 'project/demo/imagesemployee/OliverGreystone.png', 'Oliver Greystone delivers building exterior painting, using high-quality materials to protect and enhance the appearance of structures.', 59, '0913-901-2345'),
(60, 'Annabelle Windsor', 2, 'Available', 'project/demo/imagesemployee/AnnabelleWindsor.png', 'Annabelle Windsor brings style to dining spaces with restaurant or cafe wall painting, combining warm tones and creative designs.', 60, '0920-012-3456'),
(61, 'Matthew Sterling', 2, 'Available', 'project/demo/imagesemployee/MatthewSterling.png', 'Matthew Sterling creates custom mural paintings, adding artistic designs that turn walls into masterpieces.', 61, '0934-123-4567'),
(62, 'Helena Fairchild', 2, 'Unavailable', 'project/demo/imagesemployee/HelenaFairchild.png', 'Helena Fairchild provides wall stenciling, adding intricate patterns or designs to personalize your walls.', 62, '0917-234-5678'),
(63, 'Arthur Kensington', 2, 'Available', 'project/demo/imagesemployee/ArthurKensington.png', 'Arthur Kensington applies textured wall painting, delivering modern and sophisticated finishes that elevate interiors.', 63, '0928-345-6789'),
(64, 'Beatrice Langley', 2, 'Unavailable', 'project/demo/imagesemployee/BeatriceLangley.png', 'Beatrice Langley provides faux finish painting, simulating stone, wood, or marble finishes for a luxurious look.', 64, '0939-456-7890'),
(65, 'Jacob Wadsworth', 2, 'Available', 'project/demo/imagesemployee/JacobWadsworth.png', 'Jacob Wadsworth transforms walls with chalkboard painting, ideal for homes, offices, or schools needing creative spaces.', 65, '0943-567-8901'),
(66, 'Rosalind Ashford', 2, 'Available', 'project/demo/imagesemployee/RosalindAshford.png', 'Rosalind Ashford offers wall spray painting, providing smooth and even finishes for interior and exterior projects.', 66, '0954-678-9012'),
(67, 'Lucas Redmond', 2, 'Available', 'project/demo/imagesemployee/LucasRedmond.png', 'Lucas Redmond delivers furniture spray painting, giving old furniture a refreshed and showroom-ready finish.', 67, '0965-789-0123'),
(68, 'Charlotte Beaumont', 2, 'Unavailable', 'project/demo/imagesemployee/CharlotteBeaumont.png', 'Charlotte Beaumont handles vehicle spray painting, applying seamless finishes to cars or motorcycles.', 68, '0976-890-1234'),
(69, 'Edmund Hawke', 2, 'Available', 'project/demo/imagesemployee/EdmundHawke.png', 'Edmund Hawke specializes in metal surface spray painting, providing protective coatings for gates, fences, and machinery.', 69, '0911-901-2345'),
(70, 'Juliette Hastings', 2, 'Available', 'project/demo/imagesemployee/JulietteHastings.png', 'Juliette Hastings delivers cabinet spray finishing, providing a polished look for kitchen and bathroom cabinets.', 70, '0924-012-3456'),
(71, 'Henry Weatherby', 2, 'Unavailable', 'project/demo/imagesemployee/HenryWeatherby.png', 'Henry Weatherby specializes in old paint removal, stripping outdated coatings to prepare surfaces for fresh finishes.', 71, '0938-123-4567'),
(72, 'Florence Kingsley', 2, 'Available', 'project/demo/imagesemployee/FlorenceKingsley.png', 'Florence Kingsley refinishes wooden surfaces, applying protective coatings to enhance natural beauty.', 72, '0916-234-5678'),
(73, 'Lawrence Pembroke', 2, 'Available', 'project/demo/imagesemployee/LawrencePembroke.png', 'Lawrence Pembroke offers metal surface rustproofing, applying protective coatings to ensure durability and longevity.', 73, '0921-345-6789'),
(74, 'Cora Hightower', 2, 'Available', 'project/demo/imagesemployee/CoraHightower.png', 'Cora Hightower removes graffiti from walls and surfaces, restoring their clean appearance using advanced methods.', 74, '0932-456-7890'),
(75, 'William Granville', 2, 'Available', 'project/demo/imagesemployee/WilliamGranville.png', 'William Granville handles repainting services, refreshing faded or damaged surfaces with flawless results.', 75, '0949-567-8901'),
(76, 'Everett Langley', 3, 'Available', 'project/demo/imagesemployee/EverettLangley.png', 'Everett Langley specializes in wiring installation, ensuring safe and efficient electrical systems for homes.', 80, '0958-678-9012'),
(77, 'Ophelia Ashcroft', 3, 'Unavailable', 'project/demo/imagesemployee/OpheliaAshcroft.png', 'Ophelia Ashcroft offers lighting installation, bringing bright and functional lighting to any space.', 77, '0969-789-0123'),
(78, 'Cedric Blackwell', 3, 'Available', 'project/demo/imagesemployee/CedricBlackwell.png', 'Cedric Blackwell upgrades electrical panels, improving the capacity and reliability of electrical systems.', 78, '0974-890-1234'),
(79, 'Adelaide Marsh', 3, 'Available', 'project/demo/imagesemployee/AdelaideMarsh.png', 'Adelaide Marsh handles outlet and switch installation, ensuring convenient and safe power access.', 76, '0919-901-2345'),
(80, 'Franklin Westwood', 3, 'Unavailable', 'project/demo/imagesemployee/FranklinWestwood.png', 'Franklin Westwood installs ceiling fans, combining style and comfort with efficient airflow solutions.', 79, '0922-012-3456'),
(81, 'Seraphina Blake', 3, 'Available', 'project/demo/imagesemployee/SeraphinaBlake.png', 'Seraphina Blake provides commercial wiring, ensuring durable and efficient systems for business spaces.', 85, '0917-123-4567'),
(82, 'Thaddeus Vaughn', 3, 'Available', 'project/demo/imagesemployee/ThaddeusVaughn.png', 'Thaddeus Vaughn specializes in generator installation, offering reliable backup power solutions.', 84, '0923-234-5678'),
(83, 'Arabella Hawke', 3, 'Available', 'project/demo/imagesemployee/ArabellaHawke.png', 'Arabella Hawke handles industrial equipment wiring, ensuring safe and reliable operation of heavy machinery.', 83, '0936-345-6789'),
(84, 'Rupert Kingsmill', 3, 'Available', 'project/demo/imagesemployee/RupertKingsmill.png', 'Rupert Kingsmill works on high voltage systems, delivering expertise in handling demanding electrical setups.', 81, '0945-456-7890'),
(85, 'Daphne Lockwood', 3, 'Unavailable', 'project/demo/imagesemployee/DaphneLockwood.png', 'Daphne Lockwood installs emergency lighting, enhancing safety and visibility in critical areas.', 82, '0951-567-8901'),
(86, 'Cornelius Hartley', 3, 'Available', 'project/demo/imagesemployee/CorneliusHartley.png', 'Cornelius Hartley provides electrical troubleshooting, identifying and fixing complex electrical issues.', 86, '0963-678-9012'),
(87, 'Genevieve Winslow', 3, 'Available', 'project/demo/imagesemployee/GenevieveWinslow.png', 'Genevieve Winslow specializes in circuit breaker replacement, ensuring uninterrupted and safe power flow.', 87, '0978-789-0123'),
(88, 'Percival Thorn', 3, 'Available', 'project/demo/imagesemployee/PercivalThorn.png', 'Percival Thorn installs surge protection systems, guarding electrical devices against power spikes.', 88, '0919-890-1234'),
(89, 'Celeste Fairmont', 3, 'Unavailable', 'project/demo/imagesemployee/CelesteFairmont.png', 'Celeste Fairmont inspects and repairs wiring, ensuring safety and efficiency in electrical systems.', 89, '0927-901-2345'),
(90, 'Barnaby Redgrave', 3, 'Available', 'project/demo/imagesemployee/BarnabyRedgrave.png', 'Barnaby Redgrave handles lighting maintenance, extending the lifespan and efficiency of lighting systems.', 90, '0935-012-3456'),
(91, 'Vivienne Cheswick', 3, 'Available', 'project/demo/imagesemployee/VivienneCheswick.png', 'Vivienne Cheswick installs smart lighting systems, bringing convenience and energy efficiency to homes.', 91, '0949-123-4567'),
(92, 'Tobias Pendleton', 3, 'Available', 'project/demo/imagesemployee/TobiasPendleton.png', 'Tobias Pendleton specializes in security system wiring, enhancing safety with advanced surveillance systems.', 92, '0956-234-5678'),
(93, 'Lila Wakefield', 3, 'Unavailable', 'project/demo/imagesemployee/LilaWakefield.png', 'Lila Wakefield offers home automation setup, integrating devices for seamless smart home control.', 93, '0969-345-6789'),
(94, 'Ambrose Templeton', 3, 'Available', 'project/demo/imagesemployee/AmbroseTempleton.png', 'Ambrose Templeton installs thermostats, providing precise climate control and energy savings.', 94, '0972-456-7890'),
(95, 'Esme Hargrave', 3, 'Available', 'project/demo/imagesemployee/EsmeHargrave.png', 'Esme Hargrave handles audio/visual system wiring, creating immersive entertainment experiences.', 95, '0921-567-8901'),
(96, 'Leopold Carruthers', 3, 'Available', 'project/demo/imagesemployee/LeopoldCarruthers.png', 'Leopold Carruthers installs solar panels, offering eco-friendly energy solutions.', 96, '0939-678-9012'),
(97, 'Eloise Chamberlain', 3, 'Unavailable', 'project/demo/imagesemployee/EloiseChamberlain.png', 'Eloise Chamberlain sets up EV chargers, supporting the growing need for electric vehicle infrastructure.', 97, '0943-789-0123'),
(98, 'Fletcher Somerset', 3, 'Available', 'project/demo/imagesemployee/FletcherSomerset.png', 'Fletcher Somerset provides backup power systems, ensuring reliable energy supply during outages.', 98, '0954-890-1234'),
(99, 'Margot Hastings', 3, 'Available', 'project/demo/imagesemployee/MargotHastings.png', 'Margot Hastings offers lighting design services, creating efficient and visually appealing lighting plans.', 99, '0960-901-2345'),
(100, 'Alaric Wentworth', 3, 'Available', 'project/demo/imagesemployee/AlaricWentworth.png', 'Alaric Wentworth installs data and network cabling, ensuring fast and reliable connectivity.', 100, '0974-012-3456'),
(101, 'Augustus Crowley', 7, 'Available', 'project/demo/imagesemployee/AugustusCrowley.png', 'Augustus Crowley provides general home cleaning, ensuring every room in your home is spotless and comfortable.', 101, '0917-234-5678'),
(102, 'Rosalind Harrington', 7, 'Available', 'project/demo/imagesemployee/RosalindHarrington.png', 'Rosalind Harrington specializes in kitchen deep cleaning, leaving your cooking area hygienic and sparkling clean.', 102, '0923-345-6789'),
(103, 'Silas Redcliffe', 7, 'Unavailable', 'project/demo/imagesemployee/SilasRedcliffe.png', 'Silas Redcliffe delivers bathroom cleaning, ensuring clean and sanitized spaces for your peace of mind.', 103, '0936-456-7890'),
(104, 'Clementine Barlow', 7, 'Available', 'project/demo/imagesemployee/ClementineBarlow.png', 'Clementine Barlow provides carpet and rug cleaning, restoring the freshness and beauty of your flooring.', 104, '0945-567-8901'),
(105, 'Jasper Edgeworth', 7, 'Available', 'project/demo/imagesemployee/JasperEdgeworth.png', 'Jasper Edgeworth specializes in window cleaning, ensuring streak-free and crystal-clear windows for your home.', 105, '0951-678-9012'),
(106, 'Evangeline Huxley', 7, 'Available', 'project/demo/imagesemployee/EvangelineHuxley.png', 'Evangeline Huxley offers office cleaning, creating clean and professional work environments.', 106, '0963-789-0123'),
(107, 'Maximilian Drake', 7, 'Available', 'project/demo/imagesemployee/MaximilianDrake.png', 'Maximilian Drake handles warehouse cleaning, providing thorough and efficient cleaning for large spaces.', 107, '0978-890-1234'),
(108, 'Beatrix Larkspur', 7, 'Unavailable', 'project/demo/imagesemployee/BeatrixLarkspur.png', 'Beatrix Larkspur specializes in post-construction cleaning, removing debris and dust for a clean finish.', 108, '0919-901-2345'),
(109, 'Lionel Weatherby', 7, 'Available', 'project/demo/imagesemployee/LionelWeatherby.png', 'Lionel Weatherby delivers restaurant cleaning, ensuring food-safe and hygienic dining areas.', 109, '0927-012-3456'),
(110, 'Matilda Blackthorn', 7, 'Available', 'project/demo/imagesemployee/MatildaBlackthorn.png', 'Matilda Blackthorn provides medical facility cleaning, maintaining the highest standards of cleanliness and hygiene.', 110, '0935-123-4567'),
(111, 'Alistair Pendragon', 7, 'Available', 'project/demo/imagesemployee/AlistairPendragon.png', 'Alistair Pendragon handles move-in and move-out cleaning, ensuring spaces are ready for new occupants.', 111, '0917-234-5678'),
(112, 'Cordelia Winters', 7, 'Available', 'project/demo/imagesemployee/CordeliaWinters.png', 'Cordelia Winters specializes in upholstery cleaning, restoring furniture to its original beauty.', 112, '0923-345-6789'),
(113, 'Hugo Ravenscroft', 7, 'Unavailable', 'project/demo/imagesemployee/HugoRavenscroft.png', 'Hugo Ravenscroft provides mold and mildew removal, ensuring a safe and healthy living environment.', 113, '0936-456-7890'),
(114, 'Imogen Ashworth', 7, 'Available', 'project/demo/imagesemployee/ImogenAshworth.png', 'Imogen Ashworth handles air duct cleaning, improving air quality and system efficiency in homes and businesses.', 114, '0945-567-8901'),
(115, 'Percival Blackwood', 7, 'Available', 'project/demo/imagesemployee/PercivalBlackwood.png', 'Percival Blackwood delivers floor polishing and waxing, ensuring shiny, clean, and well-maintained floors.', 115, '0951-678-9012'),
(116, 'Lavinia Greystone', 7, 'Available', 'project/demo/imagesemployee/LaviniaGreystone.png', 'Lavinia Greystone offers pressure washing, removing dirt and grime from outdoor surfaces.', 116, '0963-789-0123'),
(117, 'Phineas Winscott', 7, 'Available', 'project/demo/imagesemployee/PhineasWinscott.png', 'Phineas Winscott provides gutter cleaning, preventing blockages and ensuring proper water drainage.', 117, '0978-890-1234'),
(118, 'Arabella Fenwick', 7, 'Available', 'project/demo/imagesemployee/ArabellaFenwick.png', 'Arabella Fenwick specializes in roof cleaning, maintaining the appearance and integrity of your roof.', 118, '0919-901-2345'),
(119, 'Archibald Wainwright', 7, 'Unavailable', 'project/demo/imagesemployee/ArchibaldWainwright.png', 'Archibald Wainwright handles fence and deck cleaning, restoring outdoor spaces to their original condition.', 119, '0927-012-3456'),
(120, 'Penelope Trelawney', 7, 'Available', 'project/demo/imagesemployee/PenelopeTrelawney.png', 'Penelope Trelawney provides garden cleanup, keeping your outdoor areas tidy and well-maintained.', 120, '0935-123-4567'),
(121, 'Theodore Bellamy', 7, 'Unavailable', 'project/demo/imagesemployee/TheodoreBellamy.png', 'Theodore Bellamy offers COVID-19 disinfection services, ensuring safe and sanitized spaces.', 121, '0949-234-5678'),
(122, 'Constance Davenant', 7, 'Available', 'project/demo/imagesemployee/ConstanceDavenant.png', 'Constance Davenant provides pest control cleaning, removing unwanted pests and keeping spaces clean.', 122, '0956-345-6789'),
(123, 'Ezekiel Fairhaven', 7, 'Unavailable', 'project/demo/imagesemployee/EzekielFairhaven.png', 'Ezekiel Fairhaven handles allergy reduction cleaning, minimizing allergens for healthier indoor spaces.', 123, '0969-456-7890'),
(124, 'Anastasia Hawthorne', 7, 'Available', 'project/demo/imagesemployee/AnastasiaHawthorne.png', 'Anastasia Hawthorne specializes in odor removal cleaning, eliminating unpleasant smells and freshening spaces.', 124, '0972-567-8901'),
(125, 'Leopold Brightmoor', 7, 'Available', 'project/demo/imagesemployee/LeopoldBrightmoor.png', 'Leopold Brightmoor handles hazardous material cleanup, ensuring safe and proper removal of dangerous substances.', 125, '0915-678-9012'),
(126, 'Benedict Hawthorne', 6, 'Available', 'project/demo/imagesemployee/BenedictHawthorne.png', 'Benedict Hawthorne specializes in hardwood floor installation, delivering timeless and durable flooring solutions.', 126, '0928-789-0123'),
(127, 'Juliana Westbury', 6, 'Available', 'project/demo/imagesemployee/JulianaWestbury.png', 'Juliana Westbury handles laminate flooring installation, combining beauty and affordability for your home.', 127, '0932-890-1234'),
(128, 'Gideon Fairchild', 6, 'Unavailable', 'project/demo/imagesemployee/GideonFairchild.png', 'Gideon Fairchild provides tile flooring installation, ensuring precision and lasting durability.', 128, '0947-901-2345'),
(129, 'Eliza Waverly', 6, 'Available', 'project/demo/imagesemployee/ElizaWaverly.png', 'Eliza Waverly installs vinyl flooring, offering versatile and low-maintenance solutions for any space.', 129, '0953-012-3456'),
(130, 'Malcolm Sterling', 6, 'Available', 'project/demo/imagesemployee/MalcolmSterling.png', 'Malcolm Sterling offers floor refinishing, restoring the shine and beauty of your existing floors.', 130, '0961-123-4567'),
(131, 'Adelaide Fenwick', 6, 'Available', 'project/demo/imagesemployee/AdelaideFenwick.png', 'Adelaide Fenwick handles commercial tile installation, providing durable and professional flooring solutions.', 131, '0974-234-5678'),
(132, 'Hugo Pembroke', 6, 'Unavailable', 'project/demo/imagesemployee/HugoPembroke.png', 'Hugo Pembroke applies epoxy floor coatings, creating seamless, long-lasting, and easy-to-clean surfaces.', 132, '0917-345-6789'),
(133, 'Lavinia Carrington', 6, 'Available', 'project/demo/imagesemployee/LaviniaCarrington.png', 'Lavinia Carrington specializes in concrete floor polishing, enhancing durability and aesthetics.', 133, '0920-456-7890'),
(134, 'Elias Thornton', 6, 'Available', 'project/demo/imagesemployee/EliasThornton.png', 'Elias Thornton installs rubber flooring, providing safe and durable solutions for industrial spaces.', 134, '0934-567-8901'),
(135, 'Arabella Ravenshaw', 6, 'Available', 'project/demo/imagesemployee/ArabellaRavenshaw.png', 'Arabella Ravenshaw offers vinyl plank flooring, combining style, comfort, and easy maintenance.', 135, '0942-678-9012'),
(136, 'Cornelius Blackmoore', 6, 'Available', 'project/demo/imagesemployee/CorneliusBlackmoore.png', 'Cornelius Blackmoore specializes in floorboard replacement, ensuring your floors look and perform like new.', 136, '0958-789-0123'),
(137, 'Celia Ashcroft', 6, 'Unavailable', 'project/demo/imagesemployee/CeliaAshcroft.png', 'Celia Ashcroft provides tile grout repair, restoring the look and functionality of tiled floors.', 137, '0966-890-1234'),
(138, 'Dorian Whitaker', 6, 'Available', 'project/demo/imagesemployee/DorianWhitaker.png', 'Dorian Whitaker handles scratch removal for hardwood floors, bringing back their original shine.', 138, '0970-901-2345'),
(139, 'Felicity Belmont', 6, 'Available', 'project/demo/imagesemployee/FelicityBelmont.png', 'Felicity Belmont specializes in carpet stretching, removing wrinkles and extending carpet life.', 139, '0913-012-3456'),
(140, 'Ignatius Greyson', 6, 'Available', 'project/demo/imagesemployee/IgnatiusGreyson.png', 'Ignatius Greyson offers water damage repair, restoring floors affected by leaks and flooding.', 140, '0929-123-4567'),
(141, 'Genevieve Lockhart', 6, 'Available', 'project/demo/imagesemployee/GenevieveLockhart.png', 'Genevieve Lockhart installs decks, creating functional and beautiful outdoor living spaces.', 141, '0938-234-5678'),
(142, 'Lionel Brightmoor', 6, 'Unavailable', 'project/demo/imagesemployee/LionelBrightmoor.png', 'Lionel Brightmoor handles patio paver installation, delivering elegant and durable outdoor flooring.', 142, '0943-345-6789'),
(143, 'Tabitha Kingswell', 6, 'Available', 'project/demo/imagesemployee/TabithaKingswell.png', 'Tabitha Kingswell specializes in stamped concrete flooring, combining durability with decorative appeal.', 143, '0954-456-7890'),
(144, 'Silas Radcliffe', 6, 'Available', 'project/demo/imagesemployee/SilasRadcliffe.png', 'Silas Radcliffe offers outdoor tile installation, enhancing patios and walkways with stylish flooring.', 144, '0965-567-8901'),
(145, 'Prudence Ellington', 6, 'Available', 'project/demo/imagesemployee/PrudenceEllington.png', 'Prudence Ellington provides driveway resurfacing, improving the appearance and functionality of outdoor spaces.', 145, '0976-678-9012'),
(146, 'Bartholomew Drake', 6, 'Available', 'project/demo/imagesemployee/BartholomewDrake.png', 'Bartholomew Drake installs heated flooring systems, providing warmth and comfort underfoot.', 146, '0910-789-0123'),
(147, 'Louisa Greythorn', 6, 'Available', 'project/demo/imagesemployee/LouisaGreythorn.png', 'Louisa Greythorn handles soundproof flooring installation, reducing noise and enhancing comfort.', 147, '0924-890-1234'),
(148, 'Augustus Crowell', 6, 'Unavailable', 'project/demo/imagesemployee/AugustusCrowell.png', 'Augustus Crowell offers anti-slip flooring treatments, ensuring safety in both residential and commercial spaces.', 148, '0931-901-2345'),
(149, 'Cassandra Bellamy', 6, 'Available', 'project/demo/imagesemployee/CassandraBellamy.png', 'Cassandra Bellamy provides custom floor designs, tailoring unique and artistic solutions to fit your space.', 149, '0948-012-3456'),
(150, 'Theodore Winterbourne', 6, 'Available', 'project/demo/imagesemployee/TheodoreWinterbourne.png', 'Theodore Winterbourne specializes in sustainable flooring installation, combining eco-friendly materials with beautiful design.', 150, '0950-123-4567'),
(151, 'Randolph Whitaker', 5, 'Unavailable', 'project/demo/imagesemployee/RandolphWhitaker.png', 'Randolph Whitaker specializes in brick wall construction, delivering strong and aesthetically pleasing structures for homes.', 151, '0967-234-5678'),
(152, 'Evelina Marchwood', 5, 'Available', 'project/demo/imagesemployee/EvelinaMarchwood.png', 'Evelina Marchwood offers stone veneer installation, enhancing your home with a timeless and elegant stone finish.', 152, '0971-345-6789'),
(153, 'Cedric Halstead', 5, 'Unavailable', 'project/demo/imagesemployee/CedricHalstead.png', 'Cedric Halstead provides fireplace construction, creating cozy and stylish focal points in your living spaces.', 153, '0918-456-7890'),
(154, 'Isadora Blackstone', 5, 'Available', 'project/demo/imagesemployee/IsadoraBlackstone.png', 'Isadora Blackstone specializes in patio construction, delivering beautiful and functional outdoor spaces.', 154, '0925-567-8901'),
(155, 'Victor Ravenwood', 5, 'Available', 'project/demo/imagesemployee/VictorRavenwood.png', 'Victor Ravenwood builds retaining walls, ensuring structural support and enhancing outdoor aesthetics.', 155, '0930-678-9012'),
(156, 'Florence Wyndam', 5, 'Available', 'project/demo/imagesemployee/FlorenceWyndam.png', 'Florence Wyndam handles concrete block construction, providing durable and reliable masonry solutions.', 156, '0944-789-0123'),
(157, 'Ambrose Kingsley', 5, 'Available', 'project/demo/imagesemployee/AmbroseKingsley.png', 'Ambrose Kingsley specializes in warehouse masonry, constructing strong and efficient industrial structures.', 157, '0952-890-1234'),
(158, 'Cordelia Lancaster', 5, 'Unavailable', 'project/demo/imagesemployee/CordeliaLancaster.png', 'Cordelia Lancaster provides brick paving installation, creating elegant and functional paved surfaces.', 158, '0960-901-2345'),
(159, 'Horace Pendleton', 5, 'Available', 'project/demo/imagesemployee/HoracePendleton.png', 'Horace Pendleton builds large-scale retaining walls, delivering durable and dependable support systems.', 159, '0979-012-3456'),
(160, 'Lydia Ashworth', 5, 'Available', 'project/demo/imagesemployee/LydiaAshworth.png', 'Lydia Ashworth specializes in structural masonry repairs, restoring strength and stability to existing structures.', 160, '0915-123-4567'),
(161, 'Reginald Fairfax', 5, 'Available', 'project/demo/imagesemployee/ReginaldFairfax.png', 'Reginald Fairfax provides brick repair services, restoring damaged bricks to their original strength and beauty.', 161, '0927-234-5678'),
(162, 'Harriet Middlebrook', 5, 'Unavailable', 'project/demo/imagesemployee/HarrietMiddlebrook.png', 'Harriet Middlebrook restores stone walls, preserving their historical charm and structural integrity.', 162, '0936-345-6789'),
(163, 'Thaddeus Wentworth', 5, 'Available', 'project/demo/imagesemployee/ThaddeusWentworth.png', 'Thaddeus Wentworth handles chimney repairs, ensuring safety and functionality in your home.', 163, '0941-456-7890'),
(164, 'Ophelia Bellhaven', 5, 'Available', 'project/demo/imagesemployee/OpheliaBellhaven.png', 'Ophelia Bellhaven specializes in foundation crack repairs, restoring stability and preventing further damage.', 164, '0959-567-8901'),
(165, 'Montgomery Sinclair', 5, 'Available', 'project/demo/imagesemployee/MontgomerySinclair.png', 'Montgomery Sinclair provides concrete surface restoration, ensuring smooth and durable finishes.', 165, '0968-678-9012'),
(166, 'Helena Winthrop', 5, 'Available', 'project/demo/imagesemployee/HelenaWinthrop.png', 'Helena Winthrop constructs walkways, adding functionality and beauty to outdoor areas.', 166, '0975-789-0123'),
(167, 'Everett Hargrove', 5, 'Unavailable', 'project/demo/imagesemployee/EverettHargrove.png', 'Everett Hargrove specializes in driveway paver installation, ensuring durable and elegant surfaces.', 167, '0912-890-1234'),
(168, 'Eugenia Canford', 5, 'Available', 'project/demo/imagesemployee/EugeniaCanford.png', 'Eugenia Canford builds outdoor fireplaces, creating cozy and inviting outdoor spaces.', 168, '0923-901-2345'),
(169, 'Rupert Ashbourne', 5, 'Available', 'project/demo/imagesemployee/RupertAshbourne.png', 'Rupert Ashbourne constructs garden walls, combining strength and aesthetic appeal for landscaping needs.', 169, '0935-012-3456'),
(170, 'Beatrice Merriweather', 5, 'Available', 'project/demo/imagesemployee/BeatriceMerriweather.png', 'Beatrice Merriweather handles outdoor kitchen masonry, designing functional and stylish outdoor cooking spaces.', 170, '0949-123-4567'),
(171, 'Jasper Fenbrook', 5, 'Available', 'project/demo/imagesemployee/JasperFenbrook.png', 'Jasper Fenbrook provides custom stone carving, delivering intricate and artistic stonework for unique projects.', 171, '0951-234-5678'),
(172, 'Matilda Greystone', 5, 'Unavailable', 'project/demo/imagesemployee/MatildaGreystone.png', 'Matilda Greystone specializes in historic masonry restoration, preserving the beauty and integrity of aged structures.', 172, '0966-345-6789'),
(173, 'Alaric Blackmoor', 5, 'Available', 'project/demo/imagesemployee/AlaricBlackmoor.png', 'Alaric Blackmoor offers masonry waterproofing, protecting structures from water damage and ensuring longevity.', 173, '0973-456-7890'),
(174, 'Veronica Edgewood', 5, 'Available', 'project/demo/imagesemployee/VeronicaEdgewood.png', 'Veronica Edgewood constructs stone pillars, adding durability and elegance to outdoor and indoor spaces.', 174, '0914-567-8901'),
(175, 'Sebastian Langley', 5, 'Available', 'project/demo/imagesemployee/SebastianLangley.png', 'Sebastian Langley specializes in tuckpointing services, restoring and enhancing the appearance of masonry joints.', 175, '0926-678-9012'),
(176, 'Leopold Westcliff', 4, 'Unavailable', 'project/demo/imagesemployee/LeopoldWestcliff.png', 'Leopold Westcliff specializes in roof installation, providing durable and weather-resistant roofing solutions.', 176, '0932-789-0123'),
(177, 'Arabella Windmere', 4, 'Available', 'project/demo/imagesemployee/ArabellaWindmere.png', 'Arabella Windmere offers roof replacement services, ensuring homes have reliable and long-lasting roofs.', 177, '0946-890-1234'),
(178, 'Fletcher Redmond', 4, 'Available', 'project/demo/imagesemployee/FletcherRedmond.png', 'Fletcher Redmond installs shingle roofs, combining aesthetics and functionality for residential properties.', 178, '0958-901-2345'),
(179, 'Elinor Brightwell', 4, 'Available', 'project/demo/imagesemployee/ElinorBrightwell.png', 'Elinor Brightwell specializes in metal roof installation, offering energy-efficient and durable roofing solutions.', 179, '0963-012-3456'),
(180, 'Percival Halesworth', 4, 'Available', 'project/demo/imagesemployee/PercivalHalesworth.png', 'Percival Halesworth provides flat roof installation, ideal for modern homes and low-maintenance designs.', 180, '0977-123-4567'),
(181, 'Adelaide Kingsbury', 4, 'Unavailable', 'project/demo/imagesemployee/AdelaideKingsbury.png', 'Adelaide Kingsbury specializes in commercial roof installation, ensuring durable and cost-effective roofing systems.', 181, '0910-234-5678'),
(182, 'Nathaniel Ashcroft', 4, 'Available', 'project/demo/imagesemployee/NathanielAshcroft.png', 'Nathaniel Ashcroft handles warehouse roofing, delivering strong and reliable protection for large spaces.', 182, '0921-345-6789'),
(183, 'Vivian Langhorne', 4, 'Available', 'project/demo/imagesemployee/VivianLanghorne.png', 'Vivian Langhorne installs green roofs, providing environmentally friendly and energy-efficient roofing solutions.', 183, '0934-456-7890'),
(184, 'Tobias Whitmore', 4, 'Available', 'project/demo/imagesemployee/TobiasWhitmore.png', 'Tobias Whitmore specializes in industrial metal roofing, offering robust and long-lasting protection.', 184, '0947-567-8901'),
(185, 'Juliette Hargrove', 4, 'Unavailable', 'project/demo/imagesemployee/JulietteHargrove.png', 'Juliette Hargrove provides commercial roof repair services, restoring functionality and durability to existing roofs.', 185, '0953-678-9012'),
(186, 'Augustus Belford', 4, 'Available', 'project/demo/imagesemployee/AugustusBelford.png', 'Augustus Belford handles leak repairs, preventing water damage and ensuring roof integrity.', 186, '0961-789-0123'),
(187, 'Celeste Wainwright', 4, 'Available', 'project/demo/imagesemployee/CelesteWainwright.png', 'Celeste Wainwright provides gutter repair services, ensuring proper water drainage and protection for homes.', 187, '0970-890-1234'),
(188, 'Phineas Rosewood', 4, 'Unavailable', 'project/demo/imagesemployee/PhineasRosewood.png', 'Phineas Rosewood specializes in roof cleaning, restoring the appearance and condition of roofing surfaces.', 188, '0919-901-2345'),
(189, 'Lavinia Blackthorn', 4, 'Available', 'project/demo/imagesemployee/LaviniaBlackthorn.png', 'Lavinia Blackthorn handles shingle replacements, ensuring roofs remain strong and attractive.', 189, '0928-012-3456'),
(190, 'Cedric Holloway', 4, 'Unavailable', 'project/demo/imagesemployee/CedricHolloway.png', 'Cedric Holloway repairs roof flashing, preventing leaks and ensuring a watertight roofing system.', 190, '0936-123-4567'),
(191, 'Felicity Ravenshire', 4, 'Available', 'project/demo/imagesemployee/FelicityRavenshire.png', 'Felicity Ravenshire installs skylights, adding natural light and energy efficiency to homes.', 196, '0945-234-5678'),
(192, 'Alistair Draycott', 4, 'Available', 'project/demo/imagesemployee/AlistairDraycott.png', 'Alistair Draycott specializes in solar panel roofing, integrating eco-friendly energy solutions with roofing systems.', 197, '0956-345-6789'),
(193, 'Prudence Lockhart', 4, 'Unavailable', 'project/demo/imagesemployee/PrudenceLockhart.png', 'Prudence Lockhart provides roof insulation installation, improving energy efficiency and comfort.', 198, '0960-456-7890'),
(194, 'Cornelius Fenridge', 4, 'Available', 'project/demo/imagesemployee/CorneliusFenridge.png', 'Cornelius Fenridge offers custom roof designs, tailoring unique and functional roofing solutions.', 199, '0978-567-8901'),
(195, 'Imogen Weatherall', 4, 'Available', 'project/demo/imagesemployee/ImogenWeatherall.png', 'Imogen Weatherall handles chimney repair and maintenance, ensuring safety and functionality.', 200, '0912-678-9012'),
(196, 'Harrison Morcroft', 4, 'Available', 'project/demo/imagesemployee/HarrisonMorcroft.png', 'Harrison Morcroft installs patio roofs, providing shade and protection for outdoor living areas.', 191, '0923-789-0123'),
(197, 'Clara Edgewood', 4, 'Available', 'project/demo/imagesemployee/ClaraEdgewood.png', 'Clara Edgewood handles carport roofing, delivering durable and weather-resistant structures for vehicles.', 192, '0935-890-1234'),
(198, 'Silas Winterborne', 4, 'Unavailable', 'project/demo/imagesemployee/SilasWinterborne.png', 'Silas Winterborne specializes in gazebo roof installation, creating elegant and functional outdoor spaces.', 193, '0949-901-2345'),
(199, 'Eleanor Fairbourne', 4, 'Available', 'project/demo/imagesemployee/EleanorFairbourne.png', 'Eleanor Fairbourne provides pergola roof installation, adding style and protection to outdoor areas.', 194, '0954-012-3456'),
(200, 'Ambrose Greenfield', 4, 'Unavailable', 'project/demo/imagesemployee/AmbroseGreenfield.png', 'Ambrose Greenfield installs outdoor shed roofing, ensuring durable protection for storage spaces.', 195, '0966-123-4567');

-- --------------------------------------------------------

--
-- Table structure for table `gcash`
--

CREATE TABLE `gcash` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `account_name` varchar(255) NOT NULL,
  `phone_number` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gcash`
--

INSERT INTO `gcash` (`id`, `user_id`, `account_name`, `phone_number`) VALUES
(1, 1, 'fe mala', '09758373702'),
(2, 15, '09875432467', '09764325689');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_method` varchar(50) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `shipping_note` varchar(255) DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `shipping_fee` double DEFAULT 0,
  `shipping_status` varchar(50) NOT NULL DEFAULT 'Processing'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `total_price`, `shipping_address`, `shipping_method`, `payment_method`, `shipping_note`, `order_date`, `shipping_fee`, `shipping_status`) VALUES
(82, 1, 10574.00, '123 Main Street', 'Priority Shipping', 'CreditCard', '', '2024-12-16 17:25:06', 1250, 'delivered'),
(83, 1, 1510.00, '123 Main Street', 'Standard Shipping', 'GCash', '', '2024-12-16 17:29:56', 250, 'delivered'),
(84, 1, 9574.00, '123 Main Street', 'Standard Shipping', 'CreditCard', '', '2024-12-16 17:33:04', 250, 'shipped'),
(85, 1, 2510.00, '123 Main Street', 'Priority Shipping', 'CreditCard', '', '2024-12-16 17:58:13', 1250, 'shipped'),
(86, 1, 1510.00, '123 Main Street', 'Standard Shipping', 'COD', '', '2024-12-16 18:01:49', 250, 'Processing'),
(87, 1, 6290.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-16 18:24:01', 1250, 'Processing'),
(88, 1, 10574.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'GCash', '', '2024-12-16 18:48:00', 1250, 'Processing'),
(89, 1, 10574.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'GCash', '', '2024-12-16 18:48:02', 1250, 'Processing'),
(90, 1, 10574.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'GCash', '', '2024-12-16 18:48:09', 1250, 'Processing'),
(91, 1, 10574.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'GCash', '', '2024-12-16 18:48:16', 1250, 'Processing'),
(92, 1, 10574.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'GCash', '', '2024-12-16 18:48:56', 1250, 'Processing'),
(93, 1, 2510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'PayPal', '', '2024-12-16 18:59:36', 1250, 'Processing'),
(94, 1, 2510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-16 19:02:02', 1250, 'Processing'),
(95, 1, 86766.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Express Shipping', 'CreditCard', '', '2024-12-16 19:30:10', 750, 'Processing'),
(96, 1, 2294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-16 23:01:41', 250, 'Processing'),
(97, 1, 11086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 01:17:04', 250, 'Processing'),
(98, 1, 11086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'COD', '', '2024-12-17 02:01:48', 250, 'Processing'),
(99, 1, 11086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 02:02:44', 250, 'Processing'),
(100, 1, 12990.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 02:04:58', 250, 'Processing'),
(101, 1, 2202.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Priority Shipping', 'COD', '', '2024-12-17 05:18:05', 1250, 'Processing'),
(102, 1, 1510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 06:18:28', 250, 'Processing'),
(103, 15, 2510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-17 06:30:00', 1250, 'Processing'),
(104, 1, 6522.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 06:32:52', 250, 'Processing'),
(105, 1, 1922.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'PayPal', '', '2024-12-17 06:35:25', 1250, 'Processing'),
(106, 1, 922.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'COD', '', '2024-12-17 06:41:56', 250, 'Processing'),
(107, 1, 12034.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Express Shipping', 'CreditCard', '', '2024-12-17 07:04:38', 750, 'Processing'),
(108, 1, 12034.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Express Shipping', 'CreditCard', '', '2024-12-17 07:04:42', 750, 'Processing'),
(109, 1, 12034.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Express Shipping', 'CreditCard', '', '2024-12-17 07:04:47', 750, 'Processing'),
(110, 1, 11246.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'COD', '', '2024-12-17 07:06:44', 1250, 'Processing'),
(111, 1, 11862.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'PayPal', '', '2024-12-17 07:13:55', 1250, 'Processing'),
(112, 1, 3294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-17 07:17:54', 1250, 'Processing'),
(113, 1, 1510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 07:21:49', 250, 'Processing'),
(114, 1, 1510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 07:23:27', 250, 'Processing'),
(115, 1, 11086.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Standard Shipping', 'GCash', '', '2024-12-17 07:27:45', 250, 'Processing'),
(116, 1, 90150.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Priority Shipping', 'CreditCard', '', '2024-12-17 13:33:59', 1250, 'Processing'),
(117, 1, 155642.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Priority Shipping', 'CreditCard', '', '2024-12-17 13:39:06', 1250, 'Processing'),
(118, 1, 11086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 14:06:45', 250, 'Processing'),
(119, 1, 11086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 14:20:06', 250, 'Processing'),
(120, 1, 8282.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Express Shipping', 'CreditCard', '', '2024-12-17 14:22:13', 750, 'Processing'),
(121, 1, 3294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-17 20:53:29', 1250, 'Processing'),
(122, 1, 2294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 22:19:29', 250, 'Processing'),
(123, 1, 2294.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Standard Shipping', 'PayPal', '', '2024-12-17 22:49:08', 250, 'Processing'),
(124, 1, 15118.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Standard Shipping', 'CreditCard', '', '2024-12-17 22:52:04', 250, 'Processing'),
(125, 1, 35554.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Express Shipping', 'CreditCard', '', '2024-12-17 22:53:44', 750, 'Processing'),
(126, 1, 12086.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-17 23:05:12', 1250, 'Processing'),
(127, 1, 15558.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-17 23:10:38', 1250, 'Processing'),
(128, 1, 11842.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-17 23:16:54', 250, 'Processing'),
(129, 1, 127146.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'PayPal', '', '2024-12-17 23:36:43', 250, 'Processing'),
(130, 1, 13494.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Standard Shipping', 'CreditCard', '', '2024-12-17 23:44:50', 250, 'Processing'),
(131, 1, 17638.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'COD', '', '2024-12-17 23:48:56', 250, 'Processing'),
(132, 1, 49550.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Priority Shipping', 'CreditCard', '', '2024-12-17 23:51:40', 1250, 'Processing'),
(133, 1, 8986.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-18 00:24:44', 250, 'Processing'),
(134, 1, 48598.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Priority Shipping', 'PayPal', '', '2024-12-18 00:30:24', 1250, 'Processing'),
(135, 1, 26710.00, 'Sampaguita st., Katipunan, Aklan, Region IV-A - CALABARZON, Postal Code: 6564', 'Standard Shipping', 'CreditCard', '', '2024-12-18 00:34:16', 250, 'Processing'),
(136, 1, 2294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-18 01:53:10', 250, 'Processing'),
(137, 1, 10966.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Priority Shipping', 'GCash', '', '2024-12-18 02:03:53', 1250, 'Processing'),
(138, 1, 2154.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'COD', '', '2024-12-18 02:07:23', 250, 'Processing'),
(139, 1, 2294.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'GCash', '', '2024-12-18 15:07:19', 250, 'Processing'),
(140, 1, 4478.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-18 15:31:19', 250, 'Processing'),
(141, 1, 1062.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Standard Shipping', 'CreditCard', '', '2024-12-18 18:24:29', 250, 'Processing'),
(142, 1, 3154.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-18 18:28:28', 1250, 'Processing'),
(143, 1, 2510.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'COD', '', '2024-12-18 19:05:33', 1250, 'Processing'),
(144, 1, 13126.00, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'Express Shipping', 'CreditCard', '', '2024-12-19 00:49:40', 750, 'Processing'),
(145, 1, 25302.00, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'Priority Shipping', 'CreditCard', '', '2024-12-20 14:21:07', 1250, 'Processing');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `item_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`item_id`, `order_id`, `product_name`, `quantity`, `price`) VALUES
(218, 82, 'Sia Sandpaper', 1, 140.00),
(219, 82, 'Hacksaw', 1, 672.00),
(220, 82, 'Carpet Knife', 1, 448.00),
(221, 82, 'Bandsaw', 1, 4200.00),
(222, 82, 'Circular Saw', 1, 3080.00),
(223, 82, 'CrossCut Handsaw', 1, 784.00),
(224, 83, 'Sia Sandpaper', 1, 140.00),
(225, 83, 'Hacksaw', 1, 672.00),
(226, 83, 'Carpet Knife', 1, 448.00),
(227, 84, 'Sia Sandpaper', 1, 140.00),
(228, 84, 'Hacksaw', 1, 672.00),
(229, 84, 'Carpet Knife', 1, 448.00),
(230, 84, 'Bandsaw', 1, 4200.00),
(231, 84, 'Circular Saw', 1, 3080.00),
(232, 84, 'CrossCut Handsaw', 1, 784.00),
(233, 85, 'Sia Sandpaper', 1, 140.00),
(234, 85, 'Hacksaw', 1, 672.00),
(235, 85, 'Carpet Knife', 1, 448.00),
(236, 86, 'Sia Sandpaper', 1, 140.00),
(237, 86, 'Hacksaw', 1, 672.00),
(238, 86, 'Carpet Knife', 1, 448.00),
(239, 87, 'Sia Sandpaper', 4, 140.00),
(240, 87, 'Carpet Knife', 4, 448.00),
(241, 87, 'Hacksaw', 4, 672.00),
(242, 88, 'Sia Sandpaper', 1, 140.00),
(243, 88, 'Hacksaw', 1, 672.00),
(244, 88, 'Carpet Knife', 1, 448.00),
(245, 88, 'Bandsaw', 1, 4200.00),
(246, 88, 'Circular Saw', 1, 3080.00),
(247, 88, 'CrossCut Handsaw', 1, 784.00),
(248, 89, 'Sia Sandpaper', 1, 140.00),
(249, 89, 'Hacksaw', 1, 672.00),
(250, 89, 'Carpet Knife', 1, 448.00),
(251, 89, 'Bandsaw', 1, 4200.00),
(252, 89, 'Circular Saw', 1, 3080.00),
(253, 89, 'CrossCut Handsaw', 1, 784.00),
(254, 90, 'Sia Sandpaper', 1, 140.00),
(255, 90, 'Hacksaw', 1, 672.00),
(256, 90, 'Carpet Knife', 1, 448.00),
(257, 90, 'Bandsaw', 1, 4200.00),
(258, 90, 'Circular Saw', 1, 3080.00),
(259, 90, 'CrossCut Handsaw', 1, 784.00),
(260, 91, 'Sia Sandpaper', 1, 140.00),
(261, 91, 'Hacksaw', 1, 672.00),
(262, 91, 'Carpet Knife', 1, 448.00),
(263, 91, 'Bandsaw', 1, 4200.00),
(264, 91, 'Circular Saw', 1, 3080.00),
(265, 91, 'CrossCut Handsaw', 1, 784.00),
(266, 92, 'Sia Sandpaper', 1, 140.00),
(267, 92, 'Hacksaw', 1, 672.00),
(268, 92, 'Carpet Knife', 1, 448.00),
(269, 92, 'Bandsaw', 1, 4200.00),
(270, 92, 'Circular Saw', 1, 3080.00),
(271, 92, 'CrossCut Handsaw', 1, 784.00),
(272, 93, 'Sia Sandpaper', 1, 140.00),
(273, 93, 'Hacksaw', 1, 672.00),
(274, 93, 'Carpet Knife', 1, 448.00),
(275, 94, 'Sia Sandpaper', 1, 140.00),
(276, 94, 'Hacksaw', 1, 672.00),
(277, 94, 'Carpet Knife', 1, 448.00),
(278, 95, 'CrossCut Handsaw', 6, 784.00),
(279, 95, 'Circular Saw', 4, 3080.00),
(280, 95, 'Bandsaw', 6, 4200.00),
(281, 95, 'Cordless Circular Saw', 6, 6720.00),
(282, 95, 'Back Saw', 1, 1120.00),
(283, 95, 'Foldable Utility Knife', 6, 392.00),
(284, 96, 'Sia Sandpaper', 1, 140.00),
(285, 96, 'Hacksaw', 1, 672.00),
(286, 96, 'Carpet Knife', 1, 448.00),
(287, 96, 'CrossCut Handsaw', 1, 784.00),
(288, 97, 'Sia Sandpaper', 1, 140.00),
(289, 97, 'Hacksaw', 1, 672.00),
(290, 97, 'Carpet Knife', 1, 448.00),
(291, 97, 'CrossCut Handsaw', 1, 784.00),
(292, 97, 'Circular Saw', 1, 3080.00),
(293, 97, 'Bandsaw', 1, 4200.00),
(294, 97, 'Foldable Utility Knife', 1, 392.00),
(295, 97, 'Back Saw', 1, 1120.00),
(296, 98, 'Sia Sandpaper', 1, 140.00),
(297, 98, 'Hacksaw', 1, 672.00),
(298, 98, 'Carpet Knife', 1, 448.00),
(299, 98, 'CrossCut Handsaw', 1, 784.00),
(300, 98, 'Circular Saw', 1, 3080.00),
(301, 98, 'Bandsaw', 1, 4200.00),
(302, 98, 'Foldable Utility Knife', 1, 392.00),
(303, 98, 'Back Saw', 1, 1120.00),
(304, 99, 'Sia Sandpaper', 1, 140.00),
(305, 99, 'Hacksaw', 1, 672.00),
(306, 99, 'Carpet Knife', 1, 448.00),
(307, 99, 'CrossCut Handsaw', 1, 784.00),
(308, 99, 'Circular Saw', 1, 3080.00),
(309, 99, 'Bandsaw', 1, 4200.00),
(310, 99, 'Foldable Utility Knife', 1, 392.00),
(311, 99, 'Back Saw', 1, 1120.00),
(312, 100, 'Sia Sandpaper', 1, 140.00),
(313, 100, 'Hacksaw', 2, 672.00),
(314, 100, 'Carpet Knife', 2, 448.00),
(315, 100, 'CrossCut Handsaw', 2, 784.00),
(316, 100, 'Circular Saw', 1, 3080.00),
(317, 100, 'Bandsaw', 1, 4200.00),
(318, 100, 'Foldable Utility Knife', 1, 392.00),
(319, 100, 'Back Saw', 1, 1120.00),
(320, 101, 'Sia Sandpaper', 2, 140.00),
(321, 101, 'Hacksaw', 1, 672.00),
(322, 102, 'Sia Sandpaper', 1, 140.00),
(323, 102, 'Hacksaw', 1, 672.00),
(324, 102, 'Carpet Knife', 1, 448.00),
(325, 103, 'Sia Sandpaper', 1, 140.00),
(326, 103, 'Hacksaw', 1, 672.00),
(327, 103, 'Carpet Knife', 1, 448.00),
(328, 104, 'Sia Sandpaper', 4, 140.00),
(329, 104, 'Carpet Knife', 4, 448.00),
(330, 104, 'CrossCut Handsaw', 5, 784.00),
(331, 105, 'Hacksaw', 1, 672.00),
(332, 106, 'Hacksaw', 1, 672.00),
(333, 107, 'Hacksaw', 1, 672.00),
(334, 107, 'Sia Sandpaper', 1, 140.00),
(335, 107, 'Adjustable Workbench', 1, 4480.00),
(336, 107, 'Heavy Duty Hinge', 1, 672.00),
(337, 107, 'Multipurpose Workbench', 1, 5320.00),
(338, 108, 'Hacksaw', 1, 672.00),
(339, 108, 'Sia Sandpaper', 1, 140.00),
(340, 108, 'Adjustable Workbench', 1, 4480.00),
(341, 108, 'Heavy Duty Hinge', 1, 672.00),
(342, 108, 'Multipurpose Workbench', 1, 5320.00),
(343, 109, 'Hacksaw', 1, 672.00),
(344, 109, 'Sia Sandpaper', 1, 140.00),
(345, 109, 'Adjustable Workbench', 1, 4480.00),
(346, 109, 'Heavy Duty Hinge', 1, 672.00),
(347, 109, 'Multipurpose Workbench', 1, 5320.00),
(348, 110, 'Hacksaw', 1, 672.00),
(349, 110, 'Carpet Knife', 1, 448.00),
(350, 110, 'CrossCut Handsaw', 1, 784.00),
(351, 110, 'Sia Sandpaper', 1, 140.00),
(352, 110, 'Plastic Toolbox', 1, 1400.00),
(353, 110, 'Palm Sanding', 1, 560.00),
(354, 110, 'Nikken Sandpaper', 1, 336.00),
(355, 110, 'Flat Wood File', 1, 504.00),
(356, 110, 'Belt Sander', 1, 3640.00),
(357, 110, 'Bench Chisel', 1, 672.00),
(358, 110, 'Nut Slotting File', 1, 840.00),
(359, 111, 'Hacksaw', 1, 672.00),
(360, 111, 'Carpet Knife', 1, 448.00),
(361, 111, 'CrossCut Handsaw', 2, 784.00),
(362, 111, 'Sia Sandpaper', 1, 140.00),
(363, 111, 'Plastic Toolbox', 1, 1400.00),
(364, 111, 'Cordless Biscuit Joiner', 1, 5040.00),
(365, 111, 'Text Screw', 2, 168.00),
(366, 111, 'G Clamp', 1, 560.00),
(367, 111, 'Hex Bolts & Nuts', 1, 448.00),
(368, 112, 'Sia Sandpaper', 1, 140.00),
(369, 112, 'Hacksaw', 1, 672.00),
(370, 112, 'CrossCut Handsaw', 1, 784.00),
(371, 112, 'Carpet Knife', 1, 448.00),
(372, 113, 'Sia Sandpaper', 1, 140.00),
(373, 113, 'Hacksaw', 1, 672.00),
(374, 113, 'Carpet Knife', 1, 448.00),
(375, 114, 'Sia Sandpaper', 1, 140.00),
(376, 114, 'Hacksaw', 1, 672.00),
(377, 114, 'Carpet Knife', 1, 448.00),
(378, 115, 'Sia Sandpaper', 1, 140.00),
(379, 115, 'Hacksaw', 1, 672.00),
(380, 115, 'Carpet Knife', 1, 448.00),
(381, 115, 'CrossCut Handsaw', 1, 784.00),
(382, 115, 'Circular Saw', 1, 3080.00),
(383, 115, 'Bandsaw', 1, 4200.00),
(384, 115, 'Foldable Utility Knife', 1, 392.00),
(385, 115, 'Back Saw', 1, 1120.00),
(386, 116, 'Sia Sandpaper', 3, 140.00),
(387, 116, 'Hacksaw', 4, 672.00),
(388, 116, 'Carpet Knife', 3, 448.00),
(389, 116, 'CrossCut Handsaw', 4, 784.00),
(390, 116, 'Circular Saw', 1, 3080.00),
(391, 116, 'Bandsaw', 1, 4200.00),
(392, 116, 'Foldable Utility Knife', 1, 392.00),
(393, 116, 'Back Saw', 5, 1120.00),
(394, 116, 'Electric Drill', 5, 2520.00),
(395, 116, 'Flathead Screwdriver', 5, 224.00),
(396, 116, 'Impact Drill', 5, 2800.00),
(397, 116, 'Cordless Circular Saw', 6, 6720.00),
(398, 117, 'Sia Sandpaper', 10, 140.00),
(399, 117, 'Hacksaw', 9, 672.00),
(400, 117, 'Carpet Knife', 1, 448.00),
(401, 117, 'CrossCut Handsaw', 10, 784.00),
(402, 117, 'Circular Saw', 2, 3080.00),
(403, 117, 'Bandsaw', 3, 4200.00),
(404, 117, 'Foldable Utility Knife', 1, 392.00),
(405, 117, 'Back Saw', 8, 1120.00),
(406, 117, 'Flathead Screwdriver', 11, 224.00),
(407, 117, 'Electric Drill', 10, 2520.00),
(408, 117, 'Impact Drill', 8, 2800.00),
(409, 117, 'Cordless Circular Saw', 9, 6720.00),
(410, 118, 'Sia Sandpaper', 1, 140.00),
(411, 118, 'Hacksaw', 1, 672.00),
(412, 118, 'Carpet Knife', 1, 448.00),
(413, 118, 'CrossCut Handsaw', 1, 784.00),
(414, 118, 'Back Saw', 1, 1120.00),
(415, 118, 'Foldable Utility Knife', 1, 392.00),
(416, 118, 'Bandsaw', 1, 4200.00),
(417, 118, 'Circular Saw', 1, 3080.00),
(418, 119, 'Sia Sandpaper', 1, 140.00),
(419, 119, 'CrossCut Handsaw', 1, 784.00),
(420, 119, 'Carpet Knife', 1, 448.00),
(421, 119, 'Hacksaw', 1, 672.00),
(422, 119, 'Bandsaw', 1, 4200.00),
(423, 119, 'Foldable Utility Knife', 1, 392.00),
(424, 119, 'Back Saw', 1, 1120.00),
(425, 119, 'Circular Saw', 1, 3080.00),
(426, 120, 'Sia Sandpaper', 1, 140.00),
(427, 120, 'Carpet Knife', 1, 448.00),
(428, 120, 'CrossCut Handsaw', 1, 784.00),
(429, 120, 'Hacksaw', 1, 672.00),
(430, 120, 'Dust Mask', 1, 448.00),
(431, 120, 'Noise Muffs', 1, 840.00),
(432, 120, 'Work Gloves', 1, 392.00),
(433, 120, 'UV Goggles', 1, 728.00),
(434, 120, 'Waterproof Boots', 1, 3080.00),
(435, 121, 'Sia Sandpaper', 1, 140.00),
(436, 121, 'Hacksaw', 1, 672.00),
(437, 121, 'Carpet Knife', 1, 448.00),
(438, 121, 'CrossCut Handsaw', 1, 784.00),
(439, 122, 'Sia Sandpaper', 1, 140.00),
(440, 122, 'Hacksaw', 1, 672.00),
(441, 122, 'Carpet Knife', 1, 448.00),
(442, 122, 'CrossCut Handsaw', 1, 784.00),
(443, 123, 'Sia Sandpaper', 1, 140.00),
(444, 123, 'Hacksaw', 1, 672.00),
(445, 123, 'Carpet Knife', 1, 448.00),
(446, 123, 'CrossCut Handsaw', 1, 784.00),
(447, 124, 'Sia Sandpaper', 1, 140.00),
(448, 124, 'Hacksaw', 1, 672.00),
(449, 124, 'CrossCut Handsaw', 1, 784.00),
(450, 124, 'Carpet Knife', 1, 448.00),
(451, 124, 'Multipurpose Workbench', 1, 5320.00),
(452, 124, 'Adjustable Workbench', 1, 4480.00),
(453, 124, 'Heavy Duty Hinge', 1, 672.00),
(454, 124, 'Adjustable Hacksaw', 1, 784.00),
(455, 124, 'Folding Sawhorse', 1, 1568.00),
(456, 125, 'Sia Sandpaper', 1, 140.00),
(457, 125, 'Hacksaw', 1, 672.00),
(458, 125, 'Carpet Knife', 1, 448.00),
(459, 125, 'CrossCut Handsaw', 1, 784.00),
(460, 125, 'Back Saw', 1, 1120.00),
(461, 125, 'Foldable Utility Knife', 1, 392.00),
(462, 125, 'Bandsaw', 1, 4200.00),
(463, 125, 'Circular Saw', 1, 3080.00),
(464, 125, 'Cordless Circular Saw', 1, 6720.00),
(465, 125, 'Impact Drill', 1, 2800.00),
(466, 125, 'Electric Drill', 1, 2520.00),
(467, 125, 'Flathead Screwdriver', 1, 224.00),
(468, 125, 'White Wood Glue', 1, 280.00),
(469, 125, 'Makita Biscuit Joiner', 1, 6160.00),
(470, 125, 'Cordless Biscuit Joiner', 1, 5040.00),
(471, 125, 'Phillip Screwdriver', 1, 224.00),
(472, 126, 'Sia Sandpaper', 1, 140.00),
(473, 126, 'Hacksaw', 1, 672.00),
(474, 126, 'Carpet Knife', 1, 448.00),
(475, 126, 'CrossCut Handsaw', 1, 784.00),
(476, 126, 'Circular Saw', 1, 3080.00),
(477, 126, 'Bandsaw', 1, 4200.00),
(478, 126, 'Foldable Utility Knife', 1, 392.00),
(479, 126, 'Back Saw', 1, 1120.00),
(480, 127, 'Sia Sandpaper', 1, 140.00),
(481, 127, 'Hacksaw', 1, 672.00),
(482, 127, 'Carpet Knife', 1, 448.00),
(483, 127, 'CrossCut Handsaw', 1, 784.00),
(484, 127, 'Flathead Screwdriver', 1, 224.00),
(485, 127, 'Electric Drill', 1, 2520.00),
(486, 127, 'Impact Drill', 1, 2800.00),
(487, 127, 'Cordless Circular Saw', 1, 6720.00),
(488, 128, 'Palm Sanding', 1, 560.00),
(489, 128, 'Nikken Sandpaper', 1, 336.00),
(490, 128, 'Flat Wood File', 1, 504.00),
(491, 128, 'Belt Sander', 1, 3640.00),
(492, 128, 'Bench Chisel', 1, 672.00),
(493, 128, 'Nut Slotting File', 1, 840.00),
(494, 128, 'Carpenter\'s Square', 1, 672.00),
(495, 128, 'Framing Square', 1, 840.00),
(496, 128, 'Blue Chalk Line', 1, 448.00),
(497, 128, 'Heavy-Duty Chalk Line', 1, 560.00),
(498, 128, 'Retractable Measuring Tape', 1, 392.00),
(499, 128, 'Steel Measuring Tape', 1, 336.00),
(500, 128, 'Adjustable Combination Square', 1, 1008.00),
(501, 128, 'Combination Square', 1, 784.00),
(502, 129, 'Sia Sandpaper', 48, 140.00),
(503, 129, 'Hacksaw', 5, 672.00),
(504, 129, 'Carpet Knife', 3, 448.00),
(505, 129, 'CrossCut Handsaw', 7, 784.00),
(506, 129, 'Back Saw', 1, 1120.00),
(507, 129, 'Foldable Utility Knife', 1, 392.00),
(508, 129, 'Bandsaw', 11, 4200.00),
(509, 129, 'Circular Saw', 11, 3080.00),
(510, 129, 'Hex Bolts & Nuts', 10, 448.00),
(511, 129, 'G Clamp', 22, 560.00),
(512, 129, 'Quick Clamp', 9, 672.00),
(513, 129, 'Black Screw', 33, 168.00),
(514, 130, 'Sia Sandpaper', 1, 140.00),
(515, 130, 'Hacksaw', 6, 672.00),
(516, 130, 'Carpet Knife', 1, 448.00),
(517, 130, 'CrossCut Handsaw', 6, 784.00),
(518, 130, 'Palm Sanding', 1, 560.00),
(519, 130, 'Nikken Sandpaper', 1, 336.00),
(520, 130, 'Flat Wood File', 6, 504.00),
(521, 131, 'Sia Sandpaper', 25, 140.00),
(522, 131, 'Hacksaw', 6, 672.00),
(523, 131, 'Carpet Knife', 8, 448.00),
(524, 131, 'CrossCut Handsaw', 8, 784.00),
(525, 132, 'Sia Sandpaper', 7, 140.00),
(526, 132, 'Hacksaw', 8, 672.00),
(527, 132, 'Carpet Knife', 8, 448.00),
(528, 132, 'CrossCut Handsaw', 15, 784.00),
(529, 132, 'Plastic Toolbox', 19, 1400.00),
(530, 133, 'Hacksaw', 5, 672.00),
(531, 133, 'Carpet Knife', 5, 448.00),
(532, 133, 'CrossCut Handsaw', 4, 784.00),
(533, 134, 'Sia Sandpaper', 3, 140.00),
(534, 134, 'Hacksaw', 3, 672.00),
(535, 134, 'Carpet Knife', 7, 448.00),
(536, 134, 'CrossCut Handsaw', 1, 784.00),
(537, 134, 'Plastic Toolbox', 1, 1400.00),
(538, 134, 'Heavy Duty Hinge', 3, 672.00),
(539, 134, 'Adjustable Hacksaw', 4, 784.00),
(540, 134, 'Folding Sawhorse', 5, 1568.00),
(541, 134, 'Multipurpose Workbench', 5, 5320.00),
(542, 135, 'Hacksaw', 12, 672.00),
(543, 135, 'Carpet Knife', 6, 448.00),
(544, 135, 'Sia Sandpaper', 1, 140.00),
(545, 135, 'CrossCut Handsaw', 7, 784.00),
(546, 135, 'Back Saw', 9, 1120.00),
(547, 136, 'Sia Sandpaper', 1, 140.00),
(548, 136, 'Hacksaw', 1, 672.00),
(549, 136, 'Carpet Knife', 1, 448.00),
(550, 136, 'CrossCut Handsaw', 1, 784.00),
(551, 137, 'Sia Sandpaper', 1, 140.00),
(552, 137, 'Hacksaw', 1, 672.00),
(553, 137, 'Carpet Knife', 1, 448.00),
(554, 137, 'CrossCut Handsaw', 1, 784.00),
(555, 137, 'Circular Saw', 1, 3080.00),
(556, 137, 'Bandsaw', 1, 4200.00),
(557, 137, 'Foldable Utility Knife', 1, 392.00),
(558, 138, 'Hacksaw', 1, 672.00),
(559, 138, 'Carpet Knife', 1, 448.00),
(560, 138, 'CrossCut Handsaw', 1, 784.00),
(561, 139, 'Sia Sandpaper', 1, 140.00),
(562, 139, 'Hacksaw', 1, 672.00),
(563, 139, 'Carpet Knife', 1, 448.00),
(564, 139, 'CrossCut Handsaw', 1, 784.00),
(565, 140, 'Sia Sandpaper', 1, 140.00),
(566, 140, 'Hacksaw', 1, 672.00),
(567, 140, 'Carpet Knife', 1, 448.00),
(568, 140, 'CrossCut Handsaw', 1, 784.00),
(569, 140, 'Dust Mask', 1, 448.00),
(570, 140, 'Filter Mask', 1, 504.00),
(571, 140, 'Foam Plugs', 1, 560.00),
(572, 140, 'Ear Muffs', 1, 672.00),
(573, 141, 'Sia Sandpaper', 1, 140.00),
(574, 141, 'Hacksaw', 1, 672.00),
(575, 142, 'Hacksaw', 1, 672.00),
(576, 142, 'Carpet Knife', 1, 448.00),
(577, 142, 'CrossCut Handsaw', 1, 784.00),
(578, 143, 'Sia Sandpaper', 1, 140.00),
(579, 143, 'Hacksaw', 1, 672.00),
(580, 143, 'Carpet Knife', 1, 448.00),
(581, 144, 'Cordless Biscuit Joiner', 1, 5040.00),
(582, 144, 'Makita Biscuit Joiner', 1, 6160.00),
(583, 144, 'White Wood Glue', 1, 280.00),
(584, 144, 'Palm Sanding', 1, 560.00),
(585, 144, 'Nikken Sandpaper', 1, 336.00),
(586, 145, 'Sia Sandpaper', 1, 140.00),
(587, 145, 'Carpet Knife', 1, 448.00),
(588, 145, 'Cordless Biscuit Joiner', 1, 5040.00),
(589, 145, 'Makita Biscuit Joiner', 1, 6160.00),
(590, 145, 'White Wood Glue', 7, 280.00),
(591, 145, 'Plastic Toolbox', 3, 1400.00),
(592, 145, 'Carpenter\'s Square', 2, 672.00),
(593, 145, 'Adjustable Combination Square', 4, 1008.00),
(594, 145, 'Steel Measuring Tape', 1, 336.00),
(595, 145, 'Retractable Measuring Tape', 1, 392.00);

-- --------------------------------------------------------

--
-- Table structure for table `paypal`
--

CREATE TABLE `paypal` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `paypal_email` varchar(255) NOT NULL,
  `alternate_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paypal`
--

INSERT INTO `paypal` (`id`, `user_id`, `paypal_email`, `alternate_email`) VALUES
(9, 1, 'feane@gmail.com', 'feannemalsarte@gmail.com'),
(10, 6, 'sting@gmial.com', 's@gmail.com'),
(11, 14, 'BOBO', 'TANGIN'),
(12, 15, 'Lao@gmail.com', 'l@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `productcategories`
--

CREATE TABLE `productcategories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productcategories`
--

INSERT INTO `productcategories` (`id`, `name`) VALUES
(10, 'Cleanup Supplies'),
(3, 'Cutting Tools'),
(5, 'Drilling and Fastening Tools'),
(6, 'Joining Tools and Supplies'),
(2, 'Measuring and Marking Tools'),
(1, 'Safety Gear'),
(4, 'Shaping and Smoothing Tools'),
(8, 'Storage and Organization'),
(7, 'Support Tools'),
(9, 'Wood and Materials');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `image_path`, `category_id`) VALUES
(1, 'Dust Mask', 448.00, '/project/demo/imageproducts/dust_masks1.png', 1),
(2, 'Filter Mask', 504.00, '/project/demo/imageproducts/dust_masks2.png', 1),
(3, 'Foam Plugs', 560.00, '/project/demo/imageproducts/ear_protection1.png', 1),
(4, 'Ear Muffs', 672.00, '/project/demo/imageproducts/ear_protection2.png', 1),
(5, 'Reusable Plugs', 616.00, '/project/demo/imageproducts/ear_protection3.png', 1),
(6, 'Noise Muffs', 840.00, '/project/demo/imageproducts/ear_protection4.png', 1),
(7, 'Work Gloves', 392.00, '/project/demo/imageproducts/gloves1.png', 1),
(8, 'Heat Gloves', 448.00, '/project/demo/imageproducts/gloves2.png', 1),
(9, 'Cut Gloves', 504.00, '/project/demo/imageproducts/gloves3.png', 1),
(10, 'Safety Goggles', 672.00, '/project/demo/imageproducts/safety_goggles1.png', 1),
(11, 'UV Goggles', 728.00, '/project/demo/imageproducts/safety_goggles2.png', 1),
(12, 'Waterproof Boots', 3080.00, '/project/demo/imageproducts/steel_toed_boots1.png', 1),
(13, 'Insulated Boots', 3360.00, '/project/demo/imageproducts/steel_toed_boots2.png', 1),
(14, 'Carpenter\'s Square', 672.00, '/project/demo/imageproducts/Carpentersquare1.png', 2),
(15, 'Framing Square', 840.00, '/project/demo/imageproducts/Carpentersquare2.png', 2),
(16, 'Blue Chalk Line', 448.00, '/project/demo/imageproducts/chalkline1.png', 2),
(17, 'Heavy-Duty Chalk Line', 560.00, '/project/demo/imageproducts/chalkline2.png', 2),
(18, 'Combination Square', 784.00, '/project/demo/imageproducts/Combinationsquare1.png', 2),
(19, 'Adjustable Combination Square', 1008.00, '/project/demo/imageproducts/Combinationsquare2.png', 2),
(20, 'Steel Measuring Tape', 336.00, '/project/demo/imageproducts/measuringtape1.png', 2),
(21, 'Retractable Measuring Tape', 392.00, '/project/demo/imageproducts/measuringtape2.png', 2),
(22, 'Double-Sided Measuring Tape', 504.00, '/project/demo/imageproducts/measuringtape3.png', 2),
(23, 'Spirit Level', 616.00, '/project/demo/imageproducts/Spiritlevel1.png', 2),
(24, 'Bubble Spirit Level', 672.00, '/project/demo/imageproducts/Spiritlevel2.png', 2),
(25, 'Magnetic Spirit Level', 784.00, '/project/demo/imageproducts/Spiritlevel3.png', 2),
(27, 'Carpet Knife', 448.00, '/project/demo/imageproducts/CarpetKnife.png', 3),
(28, 'CrossCut Handsaw', 784.00, '/project/demo/imageproducts/CrossCutHandSaw.png', 3),
(29, 'Circular Saw', 3080.00, '/project/demo/imageproducts/CircularSaw.png', 3),
(30, 'Bandsaw', 4200.00, '/project/demo/imageproducts/Bandsaw.png', 3),
(31, 'Foldable Utility Knife', 392.00, '/project/demo/imageproducts/FoldableUtilityKnife.png', 3),
(32, 'Back Saw', 1120.00, '/project/demo/imageproducts/BackSaw.png', 3),
(33, 'Cordless Circular Saw', 6720.00, '/project/demo/imageproducts/CordlessCircularSaw.png', 3),
(34, 'Palm Sanding', 560.00, '/project/demo/imageproducts/PalmSanding.png', 4),
(35, 'Nikken Sandpaper', 336.00, '/project/demo/imageproducts/NikkenSandPaper.png', 4),
(36, 'Flat Wood File', 504.00, '/project/demo/imageproducts/FlatWoodFile.png', 4),
(37, 'Belt Sander', 3640.00, '/project/demo/imageproducts/BeltSander.png', 4),
(38, 'Nut Slotting File', 840.00, '/project/demo/imageproducts/NutSlottingFile.png', 4),
(39, 'Bench Chisel', 672.00, '/project/demo/imageproducts/BenchChisel.png', 4),
(40, 'Impact Drill', 2800.00, '/project/demo/imageproducts/ImpactDrill.png', 5),
(41, 'Electric Drill', 2520.00, '/project/demo/imageproducts/ElectrilDrill.png', 5),
(42, 'Flathead Screwdriver', 224.00, '/project/demo/imageproducts/FlatheadScrewdriver.png', 5),
(43, 'Phillip Screwdriver', 224.00, '/project/demo/imageproducts/PhillipScrewdriver.png', 5),
(44, 'Cordless Biscuit Joiner', 5040.00, '/project/demo/imageproducts/CordlessBiscuitJoiner.png', 6),
(45, 'Makita Biscuit Joiner', 6160.00, '/project/demo/imageproducts/MakitaBiscuitJoiner.png', 6),
(46, 'White Wood Glue', 280.00, '/project/demo/imageproducts/WhiteWoodGlue.png', 6),
(47, 'Resin Wood Adhesive', 336.00, '/project/demo/imageproducts/ResinWoodAdhesive.png', 6),
(48, 'Hex Bolts & Nuts', 448.00, '/project/demo/imageproducts/HexBolts&Nuts.png', 6),
(49, 'G Clamp', 560.00, '/project/demo/imageproducts/GClamp.png', 6),
(50, 'Quick Clamp', 672.00, '/project/demo/imageproducts/QuickClamp.png', 6),
(51, 'Black Screw', 168.00, '/project/demo/imageproducts/BlackScrew.png', 6),
(52, 'Steel Screw', 196.00, '/project/demo/imageproducts/SteelScrew.png', 6),
(53, 'Text Screw', 168.00, '/project/demo/imageproducts/TextScrew.png', 6),
(54, 'Adjustable Workbench', 4480.00, '/project/demo/imageproducts/AdjustableWorkbench.png', 7),
(55, 'Heavy Duty Hinge', 672.00, '/project/demo/imageproducts/HeavyDutyHinge.png', 7),
(56, 'Adjustable Hacksaw', 784.00, '/project/demo/imageproducts/AdjustableHacksaw.png', 7),
(57, 'Folding Sawhorse', 1568.00, '/project/demo/imageproducts/FoldingSawhorse.png', 7),
(58, 'Multipurpose Workbench', 5320.00, '/project/demo/imageproducts/MultipurposeWorkbench.png', 7),
(59, 'Plastic Toolbox', 1400.00, '/project/demo/imageproducts/PlasticToolBox.png', 8),
(60, 'Wood Sawhorse', 1680.00, '/project/demo/imageproducts/WoodSawhorse.png', 9),
(61, 'Dowel Rods', 280.00, '/project/demo/imageproducts/DowelRods.png', 9),
(62, 'Concrete Nail', 224.00, '/project/demo/imageproducts/ConcreteNail.png', 9),
(63, 'Flat Washers (1 Pack)', 196.00, '/project/demo/imageproducts/FlatWashers(1Pack).png', 9),
(64, 'Galvanized Nail', 196.00, '/project/demo/imageproducts/GalvanizeNail.png', 9),
(65, 'Dowel', 224.00, '/project/demo/imageproducts/Dowel.png', 9),
(66, 'Sia Sandpaper', 140.00, '/project/demo/imageproducts/SiaSandPaper.png', 10);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`) VALUES
(8, 'Carpenter'),
(7, 'Cleaner'),
(3, 'Electrician'),
(6, 'Flooring Specialist'),
(5, 'Mason'),
(2, 'Painter'),
(1, 'Plumber'),
(4, 'Roofer');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `service_description` text DEFAULT NULL,
  `service_price` varchar(255) DEFAULT NULL,
  `service_image_path` varchar(255) DEFAULT NULL,
  `subcategory_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`service_id`, `service_name`, `service_description`, `service_price`, `service_image_path`, `subcategory_id`, `category_id`) VALUES
(1, 'Custom Dining Table Set', 'Tailor-made dining tables and chairs', '500.50 - 1700.75', '/project/demo/imagesservices/CustomDiningTableSet.png', 1, 1),
(2, 'Custom Office Desk', 'Personalized office desks with ergonomic design', '320.25 - 1250.99', '/project/demo/imagesservices/CustomOfficeDesk.png', 1, 1),
(3, 'Custom Bed Frames', 'Wooden bed frames in various sizes and designs', '420.75 - 1550.50', '/project/demo/imagesservices/CustomBedFrames.png', 1, 1),
(4, 'Custom Bookshelves', 'Tailored bookshelves to fit any space', '210.60 - 820.40', '/project/demo/imagesservices/CustomBookshelves.png', 1, 1),
(5, 'Custom Kitchen Cabinets', 'Space-efficient and modern cabinet designs', '620.85 - 2050.99', '/project/demo/imagesservices/CustomKitchenCabinets.png', 1, 1),
(6, 'Table & Chair Repair', 'Fix scratches, loose joints, or broken parts', '55.50 - 210.30', '/project/demo/imagesservices/TableAndChairRepair.png', 2, 1),
(7, 'Wood Refinishing', 'Restore shine and color to wooden furniture', '105.10 - 315.25', '/project/demo/imagesservices/WoodRefinishing.png', 2, 1),
(8, 'Cabinet Door Repairs', 'Repair hinges, knobs, or structural issues', '42.75 - 105.50', '/project/demo/imagesservices/CabinetDoorRepairs.png', 2, 1),
(9, 'Upholstered Furniture Repair', 'Repair frames or replace fabric for chairs', '155.25 - 510.60', '/project/demo/imagesservices/UpholsteredFurnitureRepair.png', 2, 1),
(10, 'Antique Furniture Restoration', 'Preserve or restore vintage pieces', '310.40 - 1255.85', '/project/demo/imagesservices/AntiqueFurnitureRestoration.png', 2, 1),
(11, 'Partition Wall Installation', 'Seamless partitioning for homes and offices', '165.50 - 520.75', '/project/demo/imagesservices/PartitionWallInstallation.png', 3, 1),
(12, 'Pergola Installation', 'Custom pergola designs for outdoor spaces', '160.25 - 510.80', '/project/demo/imagesservices/PergolaInstallation.png', 3, 1),
(13, 'Gazebo Construction', 'Create a stylish outdoor gazebo', '152.85 - 495.45', '/project/demo/imagesservices/GazeboConstruction.png', 3, 1),
(14, 'Wooden Ceiling Beams', 'Decorative wooden beams for ceilings', '180.15 - 545.60', '/project/demo/imagesservices/WoodenCeilingBeams.png', 3, 1),
(15, 'Wooden Flooring Installation', 'Premium wooden floors installed seamlessly', '155.25 - 500.75', '/project/demo/imagesservices/WoodenFlooringInstallation.png', 3, 1),
(16, 'Fence Installation or Repair', 'Secure and stylish fencing solutions', '315.30 - 1255.85', '/project/demo/imagesservices/FenceInstallationOrRepair.png', 5, 1),
(17, 'Deck Construction and Repair', 'Build or restore outdoor wooden decks', '320.45 - 1260.60', '/project/demo/imagesservices/DeckConstructionAndRepair.png', 4, 1),
(18, 'Custom Outdoor Furniture', 'Bespoke wooden furniture for outdoor use', '310.20 - 1270.85', '/project/demo/imagesservices/CustomOutdoorFurniture.png', 4, 1),
(19, 'Wooden Outdoor Storage', 'Durable storage units for outdoor needs', '325.75 - 1255.50', '/project/demo/imagesservices/WoodenOutdoorStorage.png', 4, 1),
(20, 'Decorative Wood Carvings', 'Intricate wood carving for decorative use', '330.25 - 1265.75', '/project/demo/imagesservices/DecorativeWoodCarvings.png', 4, 1),
(21, 'Custom Wall Paneling', 'Add elegance to walls with custom panels', '405.15 - 1510.85', '/project/demo/imagesservices/CustomWallPaneling.png', 5, 1),
(22, 'Door and Window Frame Installation', 'Precise framing for doors and windows', '415.40 - 1525.60', '/project/demo/imagesservices/DoorAndWindowFrameInstallation.png', 5, 1),
(23, 'Cabinet and Shelf Setup', 'Efficient and stylish storage solutions', '430.25 - 1530.70', '/project/demo/imagesservices/CabinetAndShelfSetup.png', 5, 1),
(24, 'Staircase Installation', 'Custom staircase design and installation', '420.45 - 1505.90', '/project/demo/imagesservices/StaircaseInstallation.png', 5, 1),
(25, 'Custom Wooden Signage', 'Stylish wooden signs for homes or businesses', '400.30 - 1500.50', '/project/demo/imagesservices/FenceInstallationOrRepair.png', 4, 1),
(26, 'Water Pipe Installation', 'Install water supply lines for new properties', '100.00 - 400.00', '/project/demo/imagesservices/WaterPipeInstallation.png', 6, 5),
(27, 'Faucet and Sink Installation', 'Setup of kitchen or bathroom faucets and sinks', '120.00 - 450.00', '/project/demo/imagesservices/FaucetAndSinkInstallation.png', 6, 5),
(28, 'Toilet Installation', 'Install toilets with water-saving systems', '150.00 - 500.00', '/project/demo/imagesservices/ToiletInstallation.png', 6, 5),
(29, 'Shower System Installation', 'Rain showers or standard shower setups', '300.00 - 1200.00', '/project/demo/imagesservices/ShowerSystemInstallation.png', 6, 5),
(30, 'Water Heater Installation', 'Energy-efficient water heater systems', '400.00 - 1500.00', '/project/demo/imagesservices/WaterHeaterInstallation.png', 6, 5),
(31, 'Pipe Leak Repairs', 'Quick fixes for leaky pipes', '50.00 - 200.00', '/project/demo/imagesservices/PipeLeakRepairs.png', 7, 5),
(32, 'Clogged Drain Clearing', 'Unclog kitchen, bathroom, or outdoor drains', '80.00 - 250.00', '/project/demo/imagesservices/CloggedDrainClearing.png', 7, 5),
(33, 'Toilet Repair Services', 'Fix flushing issues, leaks, or blockages', '120.00 - 400.00', '/project/demo/imagesservices/ToiletRepairServices.png', 7, 5),
(34, 'Water Heater Repairs', 'Resolve heating problems or leaks', '100.00 - 300.00', '/project/demo/imagesservices/WaterHeaterRepairs.png', 7, 5),
(35, 'Faucet Repairs', 'Stop drips, fix handles, or replace parts', '50.00 - 150.00', '/project/demo/imagesservices/FaucetRepairs.png', 7, 5),
(36, 'Drain Cleaning Services', 'Prevent clogs and buildup with regular cleaning', '80.00 - 250.00', '/project/demo/imagesservices/DrainCleaningServices.png', 8, 5),
(37, 'Septic Tank Maintenance', 'Routine pumping and cleaning of septic tanks', '150.00 - 400.00', '/project/demo/imagesservices/SepticTankMaintenance.png', 8, 5),
(38, 'Water Heater Maintenance', 'Flush sediment and check system efficiency', '200.00 - 500.00', '/project/demo/imagesservices/WaterHeaterMaintenance.png', 8, 5),
(39, 'Gutter Cleaning Service', 'Remove debris from gutters and downspouts', '50.00 - 150.00', '/project/demo/imagesservices/GutterCleaningService.png', 8, 5),
(40, 'Pipe Inspection Service', 'Use cameras to inspect pipes for damage', '100.00 - 300.00', '/project/demo/imagesservices/PipeInspectionService.png', 8, 5),
(41, 'Emergency Pipe Burst Repair', 'Quick fixes for broken or leaking pipes', '200.00 - 700.00', '/project/demo/imagesservices/EmergencyPipeBurstRepair.png', 9, 5),
(42, 'Flooding Resolution Services', 'Remove water and repair causes of flooding', '300.00 - 900.00', '/project/demo/imagesservices/FloodingResolutionServices.png', 9, 5),
(43, 'Emergency Clogged Drain Clearing', 'Unblock severe drain clogs on short notice', '100.00 - 500.00', '/project/demo/imagesservices/EmergencyCloggedDrainClearing.png', 9, 5),
(44, 'Sewer Backup Emergency Fix', 'Address sewer overflows to prevent damage', '250.00 - 750.00', '/project/demo/imagesservices/SewerBackupEmergencyFix.png', 9, 5),
(45, 'Toilet Overflow Repair', 'Resolve overflowing or blocked toilets', '150.00 - 450.00', '/project/demo/imagesservices/ToiletOverflowRepair.png', 9, 5),
(46, 'Rainwater Harvesting System Install', 'Collect and store rainwater for reuse', '500.00 - 2000.00', '/project/demo/imagesservices/RainwaterHarvestingSystemInstall.png', 10, 5),
(47, 'Greywater Recycling System Setup', 'Recycle water for non-drinking purposes', '800.00 - 3000.00', '/project/demo/imagesservices/GreywaterRecyclingSystemSetup.png', 10, 5),
(48, 'Hydro-Jetting Services', 'High-pressure water cleaning for stubborn clogs', '200.00 - 600.00', '/project/demo/imagesservices/HydroJettingServices.png', 10, 5),
(49, 'Trenchless Pipe Replacement', 'Replace underground pipes without digging', '1000.00 - 4000.00', '/project/demo/imagesservices/TrenchlessPipeReplacement.png', 10, 5),
(50, 'Water Filtration System Install', 'Purify water for residential or commercial use', '1500.00 - 5000.00', '/project/demo/imagesservices/WaterFiltrationSystemInstall.png', 10, 5),
(51, 'Interior Wall Painting', 'High-quality painting for living spaces', '54.00 - 180.00', '/project/demo/imagesservices/InteriorWallPainting.png', 11, 2),
(52, 'Ceiling Painting', 'Freshen up ceilings with smooth paint finishes', '36.00 - 90.00', '/project/demo/imagesservices/CeilingPainting.png', 11, 2),
(53, 'Exterior Wall Painting', 'Durable paint for outdoor walls', '25.70 - 53.40', '/project/demo/imagesservices/ExteriorWallPainting.png', 11, 2),
(54, 'Fence and Gate Painting', 'Protect and enhance fences or gates', '54.00 - 144.00', '/project/demo/imagesservices/FenceAndGatePainting.png', 11, 2),
(55, 'Accent Wall Painting', 'Create feature walls with unique colors', '45.00 - 126.00', '/project/demo/imagesservices/AccentWallPainting.png', 11, 2),
(56, 'Office Interior Painting', 'Paint offices to create a productive workspace', '90.00 - 360.00', '/project/demo/imagesservices/OfficeInteriorPainting.png', 12, 2),
(57, 'Warehouse Wall Painting', 'Durable coatings for industrial spaces', '2.70 - 7.20', '/project/demo/imagesservices/WarehouseWallPainting.png', 12, 2),
(58, 'Floor Epoxy Coating', 'Protective and decorative floor finishes', '9.00 - 21.60', '/project/demo/imagesservices/FloorEpoxyCoating.png', 12, 2),
(59, 'Building Exterior Painting', 'Weatherproof and aesthetic exterior finishes', '3.60 - 9.00', '/project/demo/imagesservices/BuildingExteriorPainting.png', 12, 2),
(60, 'Restaurant or Cafe Painting', 'Stylish and inviting designs for dining spaces', '180.00 - 540.00', '/project/demo/imagesservices/RestaurantOrCafePainting.png', 12, 2),
(61, 'Custom Mural Painting', 'Artistic wall designs for homes or businesses', '180.00 - 900.00', '/project/demo/imagesservices/CustomMuralPainting.png', 13, 2),
(62, 'Wall Stenciling', 'Add patterns or designs using stencils', '90.00 - 270.00', '/project/demo/imagesservices/WallStenciling.png', 13, 2),
(63, 'Textured Wall Painting', 'Create textured finishes for modern interiors', '144.00 - 360.00', '/project/demo/imagesservices/TexturedWallPainting.png', 13, 2),
(64, 'Faux Finish Painting', 'Simulate stone, wood, or marble finishes', '180.00 - 540.00', '/project/demo/imagesservices/FauxFinishPainting.png', 13, 2),
(65, 'Chalkboard Wall Painting', 'Paint walls with chalkboard surfaces', '90.00 - 216.00', '/project/demo/imagesservices/ChalkboardWallPainting.png', 13, 2),
(66, 'Wall Spray Painting', 'Smooth finishes for interior or exterior walls', '180.00 - 540.00', '/project/demo/imagesservices/WallSprayPainting.png', 14, 2),
(67, 'Furniture Spray Painting', 'Quick and even finish for wood or metal items', '90.00 - 270.00', '/project/demo/imagesservices/FurnitureSprayPainting.png', 14, 2),
(68, 'Vehicle Spray Painting', 'Repaint or touch up cars or motorcycles', '270.00 - 900.00', '/project/demo/imagesservices/VehicleSprayPainting.png', 14, 2),
(69, 'Metal Surface Spray Painting', 'Protect fences, gates, or machinery', '90.00 - 360.00', '/project/demo/imagesservices/MetalSurfaceSprayPainting.png', 14, 2),
(70, 'Cabinet Spray Finishing', 'Professional spray-painted cabinet finishes', '126.00 - 360.00', '/project/demo/imagesservices/CabinetSprayFinishing.png', 14, 2),
(71, 'Old Paint Removal', 'Strip old paint from walls or surfaces', '54.00 - 180.00', '/project/demo/imagesservices/OldPaintRemoval.png', 15, 2),
(72, 'Wooden Surface Refinishing', 'Recoat or refinish wooden furniture or panels', '90.00 - 270.00', '/project/demo/imagesservices/WoodenSurfaceRefinishing.png', 15, 2),
(73, 'Metal Surface Rustproofing', 'Remove rust and recoat metal surfaces', '90.00 - 360.00', '/project/demo/imagesservices/MetalSurfaceRustproofing.png', 15, 2),
(74, 'Graffiti Removal Services', 'Remove unwanted graffiti from walls or surfaces', '90.00 - 270.00', '/project/demo/imagesservices/GraffitiRemovalServices.png', 15, 2),
(75, 'Repainting Services', 'Refresh faded or damaged painted surfaces', '90.00 - 360.00', '/project/demo/imagesservices/RepaintingServices.png', 15, 2),
(76, 'Outlet and Switch Installation', 'Install or replace power outlets and switches', '54.00 - 144.00', '/project/demo/imagesservices/OutletSwitchInstallation.png', 16, 4),
(77, 'Lighting Installation', 'Installation of indoor or outdoor lighting', '36.00 - 90.00', '/project/demo/imagesservices/LightingInstallation.png', 16, 4),
(78, 'Ceiling Fan Installation', 'Set up or replace ceiling fans', '45.00 - 126.00', '/project/demo/imagesservices/CeilingFanInstallation.png', 16, 4),
(79, 'Electrical Panel Upgrade', 'Modernize or upgrade outdated panels', '2.70 - 5.40', '/project/demo/imagesservices/ElectricalPanelUpgrade.png', 16, 4),
(80, 'Wiring Installation', 'Full home wiring for new or renovated spaces', '54.00 - 180.00', '/project/demo/imagesservices/WiringInstallation.png', 16, 4),
(81, 'High Voltage Systems', 'Maintenance or installation of high-voltage systems', '3.60 - 9.00', '/project/demo/imagesservices/HighVoltageSystems.png', 17, 4),
(82, 'Emergency Lighting Installation', 'Install safety and emergency lighting systems', '180.00 - 540.00', '/project/demo/imagesservices/EmergencyLightingInstallation.png', 17, 4),
(83, 'Industrial Equipment Wiring', 'Power connections for machinery and equipment', '9.00 - 21.60', '/project/demo/imagesservices/IndustrialEquipmentWiring.png', 17, 4),
(84, 'Generator Installation', 'Install backup power systems', '2.70 - 7.20', '/project/demo/imagesservices/GeneratorInstallation.png', 17, 4),
(85, 'Commercial Wiring', 'Electrical wiring for offices or retail spaces', '90.00 - 360.00', '/project/demo/imagesservices/CommercialWiring.png', 17, 4),
(86, 'Electrical Troubleshooting', 'Diagnose and repair electrical issues', '180.00 - 900.00', '/project/demo/imagesservices/ElectricalTroubleshooting.png', 18, 4),
(87, 'Circuit Breaker Replacement', 'Replace faulty or old circuit breakers', '90.00 - 270.00', '/project/demo/imagesservices/CircuitBreakerReplacement.png', 18, 4),
(88, 'Surge Protection Installation', 'Install protection for appliances against power surges', '144.00 - 360.00', '/project/demo/imagesservices/SurgeProtectionInstallation.png', 18, 4),
(89, 'Wiring Inspection and Repair', 'Inspect and fix damaged or unsafe wiring', '180.00 - 540.00', '/project/demo/imagesservices/WiringInspectionRepair.png', 18, 4),
(90, 'Lighting Maintenance', 'Replace or repair faulty light fixtures', '90.00 - 216.00', '/project/demo/imagesservices/LightingMaintenance.png', 18, 4),
(91, 'Smart Lighting Installation', 'Install automated and app-controlled lighting', '180.00 - 540.00', '/project/demo/imagesservices/SmartLightingInstallation.png', 19, 4),
(92, 'Security System Wiring', 'Set up electrical wiring for CCTV and alarms', '90.00 - 270.00', '/project/demo/imagesservices/SecuritySystemWiring.png', 19, 4),
(93, 'Home Automation Setup', 'Integrate smart devices for a connected home', '270.00 - 900.00', '/project/demo/imagesservices/HomeAutomationSetup.png', 19, 4),
(94, 'Thermostat Installation', 'Install smart thermostats for energy efficiency', '90.00 - 360.00', '/project/demo/imagesservices/ThermostatInstallation.png', 19, 4),
(95, 'Audio/Visual System Wiring', 'Set up wiring for entertainment systems', '126.00 - 360.00', '/project/demo/imagesservices/AudioVisualSystemWiring.png', 19, 4),
(96, 'Solar Panel Installation', 'Install solar panels for energy efficiency', '54.00 - 180.00', '/project/demo/imagesservices/SolarPanelInstallation.png', 20, 4),
(97, 'EV Charger Installation', 'Install electric vehicle charging stations', '90.00 - 270.00', '/project/demo/imagesservices/EVChargerInstallation.png', 20, 4),
(98, 'Backup Power Systems', 'Set up and maintain UPS or battery backups', '90.00 - 360.00', '/project/demo/imagesservices/BackupPowerSystems.png', 20, 4),
(99, 'Lighting Design Services', 'Plan and install customized lighting solutions', '90.00 - 270.00', '/project/demo/imagesservices/LightingDesignServices.png', 20, 4),
(100, 'Data and Network Cabling', 'Set up structured cabling for networks', '90.00 - 360.00', '/project/demo/imagesservices/DataNetworkCabling.png', 20, 4),
(101, 'General Home Cleaning', 'Thorough cleaning of living spaces, including dusting, vacuuming, and mopping', '36.00 - 90.00', '/project/demo/imagesservices/GeneralHomeCleaning.png', 21, 6),
(102, 'Kitchen Deep Cleaning', 'Specialized cleaning for kitchen appliances, countertops, and cabinets', '45.00 - 108.00', '/project/demo/imagesservices/KitchenDeepCleaning.png', 21, 6),
(103, 'Bathroom Cleaning', 'Scrubbing and sanitizing bathroom tiles, sinks, and toilets', '27.00 - 54.00', '/project/demo/imagesservices/BathroomCleaning.png', 21, 6),
(104, 'Carpet and Rug Cleaning', 'Steam or deep cleaning for carpets and rugs', '18.00 - 63.00', '/project/demo/imagesservices/CarpetAndRugCleaning.png', 21, 6),
(105, 'Window Cleaning', 'Interior and exterior window cleaning', '18.00 - 72.00', '/project/demo/imagesservices/WindowCleaning.png', 21, 6),
(106, 'Office Cleaning', 'Daily or periodic cleaning for offices and workspaces', '54.00 - 180.00', '/project/demo/imagesservices/OfficeCleaning.png', 22, 6),
(107, 'Warehouse Cleaning', 'Cleaning industrial warehouses, including dust removal and floor polishing', '180.00 - 900.00', '/project/demo/imagesservices/WarehouseCleaning.png', 22, 6),
(108, 'Post-Construction Cleaning', 'Removal of debris, dust, and paint residues after construction', '270.00 - 900.00', '/project/demo/imagesservices/PostConstructionCleaning.png', 22, 6),
(109, 'Restaurant Cleaning', 'Sanitizing kitchens, dining areas, and equipment', '90.00 - 360.00', '/project/demo/imagesservices/RestaurantCleaning.png', 22, 6),
(110, 'Medical Facility Cleaning', 'Disinfection and cleaning of clinics or hospitals', '180.00 - 540.00', '/project/demo/imagesservices/MedicalFacilityCleaning.png', 22, 6),
(111, 'Move-In/Move-Out Cleaning', 'Comprehensive cleaning before or after moving in/out', '90.00 - 270.00', '/project/demo/imagesservices/MoveInMoveOutCleaning.png', 23, 6),
(112, 'Upholstery Cleaning', 'Cleaning and sanitizing sofas, chairs, and other upholstery', '36.00 - 126.00', '/project/demo/imagesservices/UpholsteryCleaning.png', 23, 6),
(113, 'Mold and Mildew Removal', 'Specialized cleaning to remove mold and mildew', '90.00 - 360.00', '/project/demo/imagesservices/MoldAndMildewRemoval.png', 23, 6),
(114, 'Air Duct Cleaning', 'Cleaning and sanitizing air ducts and HVAC systems', '54.00 - 180.00', '/project/demo/imagesservices/AirDuctCleaning.png', 23, 6),
(115, 'Floor Polishing and Waxing', 'Restore shine to hardwood or tiled floors', '54.00 - 144.00', '/project/demo/imagesservices/FloorPolishingAndWaxing.png', 23, 6),
(116, 'Pressure Washing', 'High-pressure cleaning for driveways, walls, and patios', '54.00 - 180.00', '/project/demo/imagesservices/PressureWashing.png', 24, 6),
(117, 'Gutter Cleaning', 'Clearing debris and ensuring proper water flow', '36.00 - 90.00', '/project/demo/imagesservices/GutterCleaning.png', 24, 6),
(118, 'Roof Cleaning', 'Removal of moss, dirt, and stains from rooftops', '90.00 - 270.00', '/project/demo/imagesservices/RoofCleaning.png', 24, 6),
(119, 'Fence and Deck Cleaning', 'Restoring fences and decks to their original condition', '54.00 - 144.00', '/project/demo/imagesservices/FenceAndDeckCleaning.png', 24, 6),
(120, 'Garden Cleanup', 'Clearing leaves, debris, and overgrowth', '45.00 - 126.00', '/project/demo/imagesservices/GardenCleanup.png', 24, 6),
(121, 'COVID-19 Disinfection', 'Professional sanitization for homes and businesses', '90.00 - 270.00', '/project/demo/imagesservices/COVID19Disinfection.png', 25, 6),
(122, 'Pest Control Cleaning', 'Clean and disinfect after pest extermination', '90.00 - 360.00', '/project/demo/imagesservices/PestControlCleaning.png', 25, 6),
(123, 'Allergy Reduction Cleaning', 'Remove allergens such as dust, pet dander, and pollen', '54.00 - 144.00', '/project/demo/imagesservices/AllergyReductionCleaning.png', 25, 6),
(124, 'Odor Removal Cleaning', 'Eliminate unpleasant odors with deep cleaning solutions', '72.00 - 180.00', '/project/demo/imagesservices/OdorRemovalCleaning.png', 25, 6),
(125, 'Hazardous Material Cleanup', 'Cleaning and disposal of hazardous substances', '270.00 - 900.00', '/project/demo/imagesservices/HazardousMaterialCleanup.png', 25, 6),
(126, 'Hardwood Floor Installation', 'Durable and elegant hardwood floor installation', '540.00 - 1800.00', '/project/demo/imagesservices/HardwoodFloorInstallation.png', 26, 8),
(127, 'Laminate Flooring Installation', 'Affordable and quick laminate floor setups', '360.00 - 900.00', '/project/demo/imagesservices/LaminateFlooringInstallation.png', 26, 8),
(128, 'Tile Flooring Installation', 'Ceramic or porcelain tiles for any room', '450.00 - 1200.00', '/project/demo/imagesservices/TileFlooringInstallation.png', 26, 8),
(129, 'Vinyl Flooring Installation', 'Water-resistant and low-maintenance vinyl floors', '360.00 - 900.00', '/project/demo/imagesservices/VinylFlooringInstallation.png', 26, 8),
(130, 'Floor Refinishing', 'Restore the beauty and shine of worn hardwood floors', '450.00 - 1200.00', '/project/demo/imagesservices/FloorRefinishing.png', 26, 8),
(131, 'Commercial Tile Installation', 'Durable tile solutions for high-traffic areas', '900.00 - 3600.00', '/project/demo/imagesservices/CommercialTileInstallation.png', 27, 8),
(132, 'Epoxy Floor Coating', 'Protective and decorative epoxy coatings for floors', '450.00 - 1800.00', '/project/demo/imagesservices/EpoxyFloorCoating.png', 27, 8),
(133, 'Concrete Floor Polishing', 'High-gloss finish for industrial concrete floors', '540.00 - 1800.00', '/project/demo/imagesservices/ConcreteFloorPolishing.png', 27, 8),
(134, 'Rubber Flooring Installation', 'Safe and durable rubber floors for gyms or offices', '900.00 - 2700.00', '/project/demo/imagesservices/RubberFlooringInstallation.png', 27, 8),
(135, 'Vinyl Plank Flooring', 'Modern vinyl planks for large commercial spaces', '720.00 - 2400.00', '/project/demo/imagesservices/VinylPlankFlooring.png', 27, 8),
(136, 'Floorboard Replacement', 'Replace damaged or warped wooden floorboards', '180.00 - 540.00', '/project/demo/imagesservices/FloorboardReplacement.png', 28, 8),
(137, 'Tile Grout Repair', 'Fix and reseal damaged or discolored tile grout', '90.00 - 270.00', '/project/demo/imagesservices/TileGroutRepair.png', 28, 8),
(138, 'Scratch Removal for Hardwood Floors', 'Buff out scratches and restore shine to hardwood', '180.00 - 450.00', '/project/demo/imagesservices/ScratchRemoval.png', 28, 8),
(139, 'Carpet Stretching', 'Eliminate wrinkles and bulges in carpet flooring', '90.00 - 360.00', '/project/demo/imagesservices/CarpetStretching.png', 28, 8),
(140, 'Water Damage Repair', 'Repair and restore floors damaged by water', '360.00 - 1200.00', '/project/demo/imagesservices/WaterDamageRepair.png', 28, 8),
(141, 'Deck Installation', 'Custom wooden or composite deck flooring', '900.00 - 3600.00', '/project/demo/imagesservices/DeckInstallation.png', 29, 8),
(142, 'Patio Paver Installation', 'Install durable and decorative patio flooring', '540.00 - 1800.00', '/project/demo/imagesservices/PatioPaverInstallation.png', 29, 8),
(143, 'Stamped Concrete Flooring', 'Design-rich concrete finishes for outdoor areas', '900.00 - 2700.00', '/project/demo/imagesservices/StampedConcreteFlooring.png', 29, 8),
(144, 'Outdoor Tile Installation', 'Weather-resistant tile solutions for patios', '540.00 - 1500.00', '/project/demo/imagesservices/OutdoorTileInstallation.png', 29, 8),
(145, 'Driveway Resurfacing', 'Restore and protect your driveway with durable coatings', '720.00 - 2400.00', '/project/demo/imagesservices/DrivewayResurfacing.png', 29, 8),
(146, 'Heated Flooring Installation', 'Install radiant heating systems under flooring', '1800.00 - 5400.00', '/project/demo/imagesservices/HeatedFlooringInstallation.png', 30, 8),
(147, 'Soundproof Flooring Installation', 'Reduce noise with specialized soundproof flooring', '900.00 - 2700.00', '/project/demo/imagesservices/SoundproofFlooringInstallation.png', 30, 8),
(148, 'Anti-Slip Flooring Treatment', 'Add anti-slip coatings for safety on floors', '450.00 - 1200.00', '/project/demo/imagesservices/AntiSlipFlooringTreatment.png', 30, 8),
(149, 'Custom Floor Designs', 'Artistic and custom designs for unique flooring', '1500.00 - 4500.00', '/project/demo/imagesservices/CustomFloorDesigns.png', 30, 8),
(150, 'Sustainable Flooring Installation', 'Eco-friendly flooring materials for green buildings', '1200.00 - 3600.00', '/project/demo/imagesservices/SustainableFlooringInstallation.png', 30, 8),
(151, 'Brick Wall Construction', 'Build durable and aesthetically pleasing brick walls', '540.00 - 1800.00', '/project/demo/imagesservices/BrickWallConstruction.png', 31, 7),
(152, 'Stone Veneer Installation', 'Install stone veneers for exterior or interior walls', '720.00 - 2400.00', '/project/demo/imagesservices/StoneVeneerInstallation.png', 31, 7),
(153, 'Fireplace Construction', 'Custom-built fireplaces using brick or stone', '1200.00 - 4500.00', '/project/demo/imagesservices/FireplaceConstruction.png', 31, 7),
(154, 'Patio Construction', 'Build patios with brick, concrete, or stone', '900.00 - 3600.00', '/project/demo/imagesservices/PatioConstruction.png', 31, 7),
(155, 'Retaining Wall Construction', 'Build sturdy retaining walls for landscaping', '1800.00 - 5400.00', '/project/demo/imagesservices/RetainingWallConstruction.png', 31, 7),
(156, 'Concrete Block Construction', 'Install concrete blocks for structural walls', '1800.00 - 9000.00', '/project/demo/imagesservices/ConcreteBlockConstruction.png', 32, 7),
(157, 'Warehouse Masonry', 'Masonry work for industrial warehouse walls', '2700.00 - 9000.00', '/project/demo/imagesservices/WarehouseMasonry.png', 32, 7),
(158, 'Brick Paving Installation', 'Durable brick paving for commercial spaces', '900.00 - 3600.00', '/project/demo/imagesservices/BrickPavingInstallation.png', 32, 7),
(159, 'Large-Scale Retaining Walls', 'Build retaining walls for commercial landscaping', '3600.00 - 10800.00', '/project/demo/imagesservices/LargeScaleRetainingWalls.png', 32, 7),
(160, 'Structural Masonry Repairs', 'Repair and restore damaged masonry on commercial buildings', '1200.00 - 4500.00', '/project/demo/imagesservices/StructuralMasonryRepairs.png', 32, 7),
(161, 'Brick Repair', 'Fix cracks, chips, and loose bricks', '180.00 - 540.00', '/project/demo/imagesservices/BrickRepair.png', 33, 7),
(162, 'Stone Wall Restoration', 'Restore damaged or weathered stone walls', '360.00 - 1200.00', '/project/demo/imagesservices/StoneWallRestoration.png', 33, 7),
(163, 'Chimney Repairs', 'Repair cracks or leaks in brick chimneys', '450.00 - 1500.00', '/project/demo/imagesservices/ChimneyRepairs.png', 33, 7),
(164, 'Foundation Crack Repair', 'Seal and repair cracks in concrete foundations', '540.00 - 1800.00', '/project/demo/imagesservices/FoundationCrackRepair.png', 33, 7),
(165, 'Concrete Surface Restoration', 'Restore or resurface damaged concrete areas', '360.00 - 1200.00', '/project/demo/imagesservices/ConcreteSurfaceRestoration.png', 33, 7),
(166, 'Walkway Construction', 'Build custom walkways using brick or stone', '540.00 - 1800.00', '/project/demo/imagesservices/WalkwayConstruction.png', 34, 7),
(167, 'Driveway Paver Installation', 'Durable and decorative driveway pavers', '720.00 - 2400.00', '/project/demo/imagesservices/DrivewayPaverInstallation.png', 34, 7),
(168, 'Outdoor Fireplace Installation', 'Build custom stone or brick outdoor fireplaces', '1200.00 - 4500.00', '/project/demo/imagesservices/OutdoorFireplaceInstallation.png', 34, 7),
(169, 'Garden Wall Construction', 'Build low stone or brick walls for gardens', '450.00 - 1500.00', '/project/demo/imagesservices/GardenWallConstruction.png', 34, 7),
(170, 'Outdoor Kitchen Masonry', 'Custom masonry for outdoor kitchens', '1800.00 - 5400.00', '/project/demo/imagesservices/OutdoorKitchenMasonry.png', 34, 7),
(171, 'Custom Stone Carving', 'Artistic stone carving for decorative purposes', '900.00 - 3600.00', '/project/demo/imagesservices/CustomStoneCarving.png', 35, 7),
(172, 'Historic Masonry Restoration', 'Preserve and restore historic masonry structures', '2700.00 - 9000.00', '/project/demo/imagesservices/HistoricMasonryRestoration.png', 35, 7),
(173, 'Masonry Waterproofing', 'Add waterproof coatings to masonry surfaces', '450.00 - 1200.00', '/project/demo/imagesservices/MasonryWaterproofing.png', 35, 7),
(174, 'Stone Pillar Construction', 'Build decorative or functional stone pillars', '1200.00 - 3600.00', '/project/demo/imagesservices/StonePillarConstruction.png', 35, 7),
(175, 'Tuckpointing Services', 'Repair and replace mortar joints in brickwork', '360.00 - 1500.00', '/project/demo/imagesservices/TuckpointingServices.png', 35, 7),
(176, 'Roof Installation', 'Install new roofs using durable materials', '1800.00 - 5400.00', '/project/demo/imagesservices/RoofInstallation.png', 36, 3),
(177, 'Roof Replacement', 'Replace old or damaged roofs', '2700.00 - 7200.00', '/project/demo/imagesservices/RoofReplacement.png', 36, 3),
(178, 'Shingle Roof Installation', 'Asphalt or composite shingle installation', '1500.00 - 4500.00', '/project/demo/imagesservices/ShingleRoofInstallation.png', 36, 3),
(179, 'Metal Roof Installation', 'Durable and energy-efficient metal roofing', '3600.00 - 10800.00', '/project/demo/imagesservices/MetalRoofInstallation.png', 36, 3),
(180, 'Flat Roof Installation', 'Install flat roofing systems for modern homes', '2700.00 - 7200.00', '/project/demo/imagesservices/FlatRoofInstallation.png', 36, 3),
(181, 'Commercial Roof Installation', 'Install high-durability roofs for commercial buildings', '7200.00 - 18000.00', '/project/demo/imagesservices/CommercialRoofInstallation.png', 37, 3),
(182, 'Warehouse Roofing', 'Install or replace large-scale industrial roofs', '10800.00 - 36000.00', '/project/demo/imagesservices/WarehouseRoofing.png', 37, 3),
(183, 'Green Roof Installation', 'Eco-friendly roofing systems for commercial spaces', '5400.00 - 18000.00', '/project/demo/imagesservices/GreenRoofInstallation.png', 37, 3),
(184, 'Industrial Metal Roofing', 'Install metal roofs for warehouses or factories', '7200.00 - 21600.00', '/project/demo/imagesservices/IndustrialMetalRoofing.png', 37, 3),
(185, 'Commercial Roof Repairs', 'Fix leaks, cracks, or damage on commercial roofs', '1800.00 - 5400.00', '/project/demo/imagesservices/CommercialRoofRepairs.png', 37, 3),
(186, 'Leak Repair', 'Fix leaks to prevent water damage', '360.00 - 1200.00', '/project/demo/imagesservices/LeakRepair.png', 38, 3),
(187, 'Gutter Repair', 'Repair or replace damaged gutters', '180.00 - 540.00', '/project/demo/imagesservices/GutterRepair.png', 38, 3),
(188, 'Roof Cleaning', 'Remove debris, moss, and dirt from roofs', '180.00 - 720.00', '/project/demo/imagesservices/RoofCleaning.png', 38, 3),
(189, 'Shingle Replacement', 'Replace damaged or missing shingles', '360.00 - 900.00', '/project/demo/imagesservices/ShingleReplacement.png', 38, 3),
(190, 'Flashing Repair', 'Fix or replace flashing to prevent water entry', '540.00 - 1800.00', '/project/demo/imagesservices/FlashingRepair.png', 38, 3),
(191, 'Patio Roof Installation', 'Build durable and stylish roofs for patios', '900.00 - 3600.00', '/project/demo/imagesservices/PatioRoofInstallation.png', 39, 3),
(192, 'Carport Roofing', 'Install or replace carport roofs', '1500.00 - 4500.00', '/project/demo/imagesservices/CarportRoofing.png', 39, 3),
(193, 'Gazebo Roof Installation', 'Install decorative and weather-resistant roofs for gazebos', '1800.00 - 5400.00', '/project/demo/imagesservices/GazeboRoofInstallation.png', 39, 3),
(194, 'Pergola Roof Installation', 'Build roofs for pergolas to provide shade', '1200.00 - 3600.00', '/project/demo/imagesservices/PergolaRoofInstallation.png', 39, 3),
(195, 'Outdoor Shed Roofing', 'Install or repair roofing for outdoor sheds', '540.00 - 1800.00', '/project/demo/imagesservices/OutdoorShedRoofing.png', 39, 3),
(196, 'Skylight Installation', 'Add skylights for natural light and ventilation', '1800.00 - 5400.00', '/project/demo/imagesservices/SkylightInstallation.png', 40, 3),
(197, 'Solar Panel Roofing', 'Integrate solar panels into roofing systems', '10800.00 - 36000.00', '/project/demo/imagesservices/SolarPanelRoofing.png', 40, 3),
(198, 'Roof Insulation Installation', 'Install insulation to improve energy efficiency', '2700.00 - 7200.00', '/project/demo/imagesservices/RoofInsulationInstallation.png', 40, 3),
(199, 'Custom Roof Designs', 'Unique roofing designs tailored to client needs', '7200.00 - 21600.00', '/project/demo/imagesservices/CustomRoofDesigns.png', 40, 3),
(200, 'Chimney Repair and Maintenance', 'Fix and maintain chimneys for safety and performance', '900.00 - 3600.00', '/project/demo/imagesservices/ChimneyRepairMaintenance.png', 40, 3);

-- --------------------------------------------------------

--
-- Table structure for table `servicecategory`
--

CREATE TABLE `servicecategory` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `servicecategory`
--

INSERT INTO `servicecategory` (`category_id`, `category_name`) VALUES
(1, 'Carpentry Service'),
(6, 'Cleaning Service'),
(4, 'Electrical Service'),
(8, 'Flooring Service'),
(7, 'Masonry Service'),
(2, 'Painting Service'),
(5, 'Plumbing Service'),
(3, 'Roofing Service');

-- --------------------------------------------------------

--
-- Table structure for table `service_orders`
--

CREATE TABLE `service_orders` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `service_address` varchar(255) NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `additional_notes` varchar(255) DEFAULT NULL,
  `booking_date` datetime DEFAULT current_timestamp(),
  `service_fee` decimal(10,2) NOT NULL DEFAULT 0.00,
  `service_status` varchar(50) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service_orders`
--

INSERT INTO `service_orders` (`booking_id`, `user_id`, `total_price`, `service_address`, `payment_method`, `additional_notes`, `booking_date`, `service_fee`, `service_status`) VALUES
(1, 1, 1241.50, 'project.demo.models.Address@148590a', 'Credit Card', '', '2024-12-19 04:48:24', 1241.50, 'Pending'),
(2, 1, 2128.45, 'Manokan St., Hagonoy, Davao del Sur, Region XI - Davao Region, Postal Code: 8001', 'COD', '', '2024-12-19 05:06:02', 2128.45, 'Pending'),
(3, 1, 5941.38, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'COD', '', '2024-12-19 05:30:36', 5941.38, 'Pending'),
(4, 1, 500.50, 'Rho st., Jornadal, Bulacan, Region IV-A - CALABARZON, Postal Code: 7850', 'COD', '', '2024-12-19 08:05:20', 500.50, 'Pending'),
(5, 1, 3902.12, 'fo[emf ewkfkmweflkneflkwef, erfknerml;ergk;n, Agusan del Sur, Region VII - Central Visayas, Postal Code: 90ew99090', 'COD', '', '2024-12-19 09:04:40', 3902.12, 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `shipping_details`
--

CREATE TABLE `shipping_details` (
  `id` int(11) NOT NULL,
  `method` varchar(50) NOT NULL,
  `fee` decimal(10,2) NOT NULL,
  `order_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subcategory`
--

CREATE TABLE `subcategory` (
  `subcategory_id` int(11) NOT NULL,
  `subcategory_name` varchar(255) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subcategory`
--

INSERT INTO `subcategory` (`subcategory_id`, `subcategory_name`, `category_id`) VALUES
(1, 'Made-to-Order Furniture', 1),
(2, 'Furniture Repairs', 1),
(3, 'Carpentry Installation', 1),
(4, 'Custom Woodwork', 1),
(5, 'Outdoor Carpentry', 1),
(6, 'Plumbing Installation', 5),
(7, 'Plumbing Repairs', 5),
(8, 'Plumbing Maintenance', 5),
(9, 'Emergency Plumbing', 5),
(10, 'Advanced Plumbing\n', 5),
(11, 'Residential Painting', 2),
(12, 'Commercial Painting', 2),
(13, 'Custom Painting', 2),
(14, 'Spray Painting', 2),
(15, 'Painting Restoration', 2),
(16, 'Residential Power Solutions', 4),
(17, 'Commercial Energy Systems', 4),
(18, 'Repair & Circuit Maintenance', 4),
(19, 'Smart Home Wiring', 4),
(20, 'Specialized Energy Services', 4),
(21, 'Residential Cleaning', 6),
(22, 'Commercial Cleaning', 6),
(23, 'Deep Cleaning', 6),
(24, 'Outdoor Cleaning', 6),
(25, 'Specialized Cleaning', 6),
(26, 'Home Flooring', 8),
(27, 'Commercial Flooring', 8),
(28, 'Floor Repair', 8),
(29, 'Outdoor Flooring', 8),
(30, 'Special Flooring', 8),
(31, 'Home Masonry', 7),
(32, 'Commercial Masonry', 7),
(33, 'Masonry Repair', 7),
(34, 'Outdoor Masonry', 7),
(35, 'Special Masonry', 7),
(36, 'Home Roofing', 3),
(37, 'Commercial Roofing', 3),
(38, 'Roof Repair', 3),
(39, 'Outdoor Roofing', 3),
(40, 'Special Roofing', 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `contact_number` varchar(15) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `created_at`, `contact_number`, `profile_picture`) VALUES
(1, 'Fe Anne L. Malasarte', 'f@gmail.com', 'feanne', '2024-11-30 13:56:33', '09765473892', '/project/demo/pfp/profile_1_AlistairPendragon.png'),
(3, 'Joevan B. Capote', 'joevan@gmail.com', '123', '2024-11-30 14:19:48', '09758373702', NULL),
(4, 'admin', 'a@gmail.com', '12', '2024-12-01 18:26:15', '09785454567', NULL),
(5, 'Rho Alphonce ', 'rho@gmail.com', 'admin', '2024-12-04 04:26:10', NULL, NULL),
(6, 'Sting Tarrazona', 's@gmail.com', '123', '2024-12-06 01:32:02', '09754848234', NULL),
(7, 'Janna Malasarte', 'j@gmail.com', 'janna', '2024-12-07 19:06:32', NULL, NULL),
(8, 'Janna Marie Malasarte', 'janna@gmail.com', 'janna', '2024-12-08 14:16:41', NULL, NULL),
(9, 'charisse priego', 'c@gmail.com', 'charisse', '2024-12-12 06:02:06', NULL, NULL),
(10, 'Kent Leonel Sevellino', 'kent@gmail.com', '123456', '2024-12-12 06:41:24', NULL, NULL),
(13, 'Rho Alphonce Ggerni', 'rhoalphonce@gmail.com', 'admin', '2024-12-13 05:47:51', NULL, NULL),
(14, 'ryan shayne', 'ryanshayne@gmail.com', 'rygaspillo', '2024-12-13 06:00:13', '09543989202', '/project/demo/pfp/profile_14_AlistairPendragon.png'),
(15, 'lao', 'lao@gmail.com', 'lao', '2024-12-13 06:10:44', NULL, '/project/demo/pfp/profile_15_AlaricBlackmoor.png'),
(19, 'anne', 'anne@gmail.com', 'anne', '2024-12-17 05:13:19', NULL, NULL),
(20, 'janice mirafuentes', 'janice@gmail.com', 'janice', '2024-12-18 11:30:48', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `booked_service`
--
ALTER TABLE `booked_service`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `creditcard`
--
ALTER TABLE `creditcard`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_user_id` (`user_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `fk_service` (`service_id`);

--
-- Indexes for table `gcash`
--
ALTER TABLE `gcash`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `paypal`
--
ALTER TABLE `paypal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `productcategories`
--
ALTER TABLE `productcategories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_name` (`role_name`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`service_id`),
  ADD KEY `subcategory_id` (`subcategory_id`);

--
-- Indexes for table `servicecategory`
--
ALTER TABLE `servicecategory`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `category_name` (`category_name`);

--
-- Indexes for table `service_orders`
--
ALTER TABLE `service_orders`
  ADD PRIMARY KEY (`booking_id`);

--
-- Indexes for table `shipping_details`
--
ALTER TABLE `shipping_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_id` (`order_id`);

--
-- Indexes for table `subcategory`
--
ALTER TABLE `subcategory`
  ADD PRIMARY KEY (`subcategory_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `booked_service`
--
ALTER TABLE `booked_service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `creditcard`
--
ALTER TABLE `creditcard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=201;

--
-- AUTO_INCREMENT for table `gcash`
--
ALTER TABLE `gcash`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=596;

--
-- AUTO_INCREMENT for table `paypal`
--
ALTER TABLE `paypal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `productcategories`
--
ALTER TABLE `productcategories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `service_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7744446;

--
-- AUTO_INCREMENT for table `servicecategory`
--
ALTER TABLE `servicecategory`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `service_orders`
--
ALTER TABLE `service_orders`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `shipping_details`
--
ALTER TABLE `shipping_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subcategory`
--
ALTER TABLE `subcategory`
  MODIFY `subcategory_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `booked_service`
--
ALTER TABLE `booked_service`
  ADD CONSTRAINT `booked_service_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `service_orders` (`booking_id`) ON DELETE CASCADE;

--
-- Constraints for table `creditcard`
--
ALTER TABLE `creditcard`
  ADD CONSTRAINT `creditcard_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `fk_service` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`);

--
-- Constraints for table `gcash`
--
ALTER TABLE `gcash`
  ADD CONSTRAINT `gcash_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `paypal`
--
ALTER TABLE `paypal`
  ADD CONSTRAINT `paypal_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `productcategories` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`subcategory_id`);

--
-- Constraints for table `shipping_details`
--
ALTER TABLE `shipping_details`
  ADD CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `subcategory`
--
ALTER TABLE `subcategory`
  ADD CONSTRAINT `subcategory_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `servicecategory` (`category_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
