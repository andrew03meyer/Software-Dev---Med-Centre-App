USE gpManagementSystem;

INSERT INTO doctors
VALUES 
(001, "andrew", "meyer", "andrew03meyer", SHA("pass")),
(002, "peter", "smith", "ps1", SHA("pass")),
(003, "peter", "hughes", "ph1", SHA("pass")),
(004, "cal", "sherv", "callum", SHA("pass")),
(005, "john", "doe", "jd1", SHA("pass")),
(006, "louis", "theobald", "lt1", SHA("egg"));

INSERT INTO patients
VALUES 
(001, "amber", "smith", "as1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk"),
(002, "thomas", "grace", "tg1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk"),
(003, "scarlett", "johns", "sj1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk"),
(004, "eep", "pope", "ep1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk"),
(005, "jane", "doe", "jad1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk"),
(006, "andy", "mayor", "am1", SHA("pass"), "0123245890", "2, your house, your street, exampleton", "bozo@skibidi.co.uk");

INSERT INTO bookings
VALUES
(001, 001, 001, "2025-02-12"),
(002, 002, 003, "2024-04-02"),
(003, 002, 005, "2024-01-05"),
(004, 004, 002, "2024-05-20"),
(005, 005, 006, "2024-04-16"),
(006, 006, 004, "2024-04-12"),
(007, 006, 004, "2024-04-19"),
(008, 001, 002, "2025-03-21"),
(009, 001, 003, "2025-04-12");

INSERT INTO past_visits
VALUES
(001, "2022-02-10", 001, 001, "e", "x"),
(002, "2023-07-30", 002, 001, "g", "h"),
(003, "1990-01-24", 001, 002, "k", ";");