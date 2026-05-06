create database vehicle_rental;
use vehicle_rental;

create table `vehicle` (
    `id` int auto_increment primary key,
    `licensePlate` varchar(20) not null unique,
    `model` varchar(100),
    `brand` varchar(50),
    `vehicleType` ENUM ('MANUAL', 'AUTOMATIC', 'SCOOTER') not null,
    -- MANUAL: xe côn tay, AUTOMATIC: xe số , SCOOTER: xe tay ga
    `pricePerDay` int not null,
    
    `status` ENUM ('AVAILABLE', 'RENTED', 'MAINTENANCE') default 'AVAILABLE',
    
    `createdAt` datetime default current_timestamp,
    `isDeleted` boolean default false
);

create table `customer` (
    `identityNumber` varchar(20) primary key,
    `fullName` varchar(100) not null,
    `phoneNumber` varchar(15),
    
    `address` varchar(255),
    
    `createdAt` datetime default current_timestamp
);

create table `rental` (
    `id` int auto_increment primary key,
    
    `customerId` varchar(20) not null,
    `vehicleId` int not null,
    
    `startTime` datetime not null,
    `expectedReturnTime` datetime not null,
    `actualReturnTime` datetime,
    
    `pricePerDay` int not null,
    
    `deposit` int default 0, --
    
    `estimatedTotal` int, -- giá dự kiến
    `extraFee` int default 0, --
    `totalAmount` int,    -- giá thực tế
    
    `status` ENUM('CREATED', 'ACTIVE', 'COMPLETED', 'CANCELLED') default 'CREATED',
    
    `createdAt` datetime default current_timestamp,
    
    foreign key (`customerId`) references `customer`(`identityNumber`),
    foreign key (`vehicleId`) references `vehicle`(`id`)
);

create table `extrafee_type` (
    `id` int auto_increment primary key,
    `name` varchar(100),       -- Trầy xe, Vỡ gương...
    `amount` int not null,          -- Số tiền phạt tiêu chuẩn cho loại phí này
    `description` varchar(255)
);

create table `rental_extrafee` (
    `id` int auto_increment primary key,
    `rentalId` int null,
    `extraFeeTypeId` int not null,

    `amount` int not null, -- Số tiền thực tế phải trả cho loại phí này (có thể khác với amount trong ExtraFeeType nếu có thỏa thuận riêng)
    `description` varchar(255), -- Mô tả chi tiết về phí này (ví dụ: "Trầy xe do khách hàng không cẩn thận")
    
    foreign key (`rentalId`) references `rental`(`id`),
    foreign key (`extraFeeTypeId`) references `extrafee_type`(`id`)
);

create table `system_config` (
    `key` varchar(50) primary key,
    `value` varchar(255) not null
);

insert into `system_config` (`key`, `value`) values
('PENALTY_PER_HOUR', '20000');

-- =========================
-- Seed data for testing
-- =========================

insert into `vehicle` (`licensePlate`, `model`, `brand`, `vehicleType`, `pricePerDay`, `status`, `isDeleted`) values
('59A1-12345', 'Vision 2024', 'Honda', 'AUTOMATIC', 150000, 'AVAILABLE', false),
('59A1-23456', 'Air Blade 160', 'Honda', 'AUTOMATIC', 220000, 'AVAILABLE', false),
('59B2-34567', 'Exciter 155', 'Yamaha', 'SCOOTER', 250000, 'RENTED', false),
('59B2-45678', 'Sirius FI', 'Yamaha', 'MANUAL', 130000, 'AVAILABLE', false),
('59C3-56789', 'Lead 125', 'Honda', 'AUTOMATIC', 180000, 'AVAILABLE', false),
('59C3-67890', 'Janus', 'Yamaha', 'AUTOMATIC', 140000, 'RENTED', false),
('59D4-11111', 'SH Mode', 'Honda', 'AUTOMATIC', 280000, 'MAINTENANCE', false),
('59D4-22222', 'Wave Alpha', 'Honda', 'MANUAL', 120000, 'AVAILABLE', false),
('59E5-33333', 'NVX 155', 'Yamaha', 'AUTOMATIC', 230000, 'AVAILABLE', false),
('59E5-44444', 'Winner X', 'Honda', 'SCOOTER', 240000, 'RENTED', false),
('59F6-55555', 'Grande', 'Yamaha', 'AUTOMATIC', 170000, 'AVAILABLE', false),
('59F6-66666', 'Vario 160', 'Honda', 'AUTOMATIC', 260000, 'AVAILABLE', false),
('59G7-77777', 'SH 125i', 'Honda', 'AUTOMATIC', 320000, 'MAINTENANCE', false),
('59G7-88888', 'Future 125', 'Honda', 'MANUAL', 160000, 'AVAILABLE', false),
('59H8-99999', 'PG-1', 'Yamaha', 'SCOOTER', 200000, 'AVAILABLE', false),
('59H8-12121', 'FreeGo', 'Yamaha', 'AUTOMATIC', 150000, 'RENTED', false),
('59K9-34343', 'Blade 110', 'Honda', 'MANUAL', 110000, 'AVAILABLE', true),
('59K9-56565', 'Jupiter', 'Yamaha', 'MANUAL', 145000, 'AVAILABLE', false);

insert into `customer` (`identityNumber`, `fullName`, `phoneNumber`, `address`) values
('079123456789', 'Nguyen Van An', '0909123456', 'Quan 1, TP.HCM'),
('079123456790', 'Tran Thi Bich', '0918234567', 'Quan 3, TP.HCM'),
('079123456791', 'Le Quoc Cuong', '0937345678', 'Quan 5, TP.HCM'),
('079123456792', 'Pham Minh Duc', '0946456789', 'Quan 7, TP.HCM'),
('079123456793', 'Hoang Ngoc Em', '0965567890', 'Quan 10, TP.HCM'),
('079123456794', 'Vo Thanh Giang', '0974678901', 'Thu Duc, TP.HCM'),
('079123456795', 'Bui Khanh Ha', '0983789012', 'Go Vap, TP.HCM'),
('079123456796', 'Dang Tuan Kiet', '0392890123', 'Binh Thanh, TP.HCM'),
('079123456797', 'Do Lan Anh', '0381901234', 'Tan Binh, TP.HCM'),
('079123456798', 'Mai Quang Long', '0379012345', 'Quan 11, TP.HCM'),
('079123456799', 'Phan Thanh Nam', '0368123456', 'Hoc Mon, TP.HCM'),
('079123456800', 'Truong My Oanh', '0357234567', 'Quan 6, TP.HCM');

insert into `extrafee_type` (`name`, `amount`, `description`) values
('Tra xe tre theo gio', 20000, 'Phat moi gio tra tre'),
('Tray xe nhe', 150000, 'Vet tray nho tren vo xe'),
('Vo guong', 250000, 'Hu hong mot guong chieu hau'),
('Mat mu bao hiem', 120000, 'Khach lam mat mu bao hiem di kem'),
('Thieu xang khi tra', 50000, 'Muc xang thap hon luc nhan xe'),
('Hu hong khoa tu', 300000, 'Khoa tu gay hoac ket'),
('Rua xe', 30000, 'Phi ve sinh khi xe qua ban'),
('Cuu ho ngoai khu vuc', 200000, 'Ho tro cuu ho xa trung tam');

insert into `rental`
(`customerId`, `vehicleId`, `startTime`, `expectedReturnTime`, `actualReturnTime`, `pricePerDay`, `deposit`, `estimatedTotal`, `extraFee`, `totalAmount`, `status`) values
('079123456789', 1,  '2026-03-01 08:00:00', '2026-03-03 08:00:00', '2026-03-03 09:10:00', 150000, 500000, 300000, 20000, 320000, 'COMPLETED'),
('079123456790', 2,  '2026-03-05 09:00:00', '2026-03-06 09:00:00', '2026-03-06 08:40:00', 220000, 500000, 220000, 0, 220000, 'COMPLETED'),
('079123456791', 3,  '2026-05-05 10:00:00', '2026-05-07 10:00:00', null,                  250000, 700000, 500000, 0, null,    'ACTIVE'),
('079123456792', 8,  '2026-05-10 07:30:00', '2026-05-12 07:30:00', null,                  120000, 400000, 240000, 0, null,    'CREATED'),
('079123456793', 5,  '2026-04-10 14:00:00', '2026-04-12 14:00:00', null,                  180000, 500000, 360000, 0, null,    'CANCELLED'),
('079123456794', 6,  '2026-05-04 16:00:00', '2026-05-06 16:00:00', null,                  140000, 400000, 280000, 0, null,    'ACTIVE'),
('079123456795', 4,  '2026-02-20 09:00:00', '2026-02-22 09:00:00', '2026-02-22 12:30:00', 130000, 300000, 260000, 190000, 450000, 'COMPLETED'),
('079123456796', 9,  '2026-02-25 12:00:00', '2026-02-28 12:00:00', '2026-02-28 11:00:00', 230000, 700000, 690000, 0, 690000, 'COMPLETED'),
('079123456797', 10, '2026-05-06 08:30:00', '2026-05-08 08:30:00', null,                  240000, 700000, 480000, 0, null,    'ACTIVE'),
('079123456798', 11, '2026-01-10 08:00:00', '2026-01-12 08:00:00', '2026-01-12 08:00:00', 170000, 400000, 340000, 30000, 370000, 'COMPLETED'),
('079123456799', 12, '2026-01-15 13:00:00', '2026-01-16 13:00:00', '2026-01-16 15:10:00', 260000, 600000, 260000, 70000, 330000, 'COMPLETED'),
('079123456800', 14, '2026-04-01 09:00:00', '2026-04-03 09:00:00', '2026-04-03 09:00:00', 160000, 400000, 320000, 0, 320000, 'COMPLETED'),
('079123456789', 15, '2026-04-15 10:00:00', '2026-04-17 10:00:00', '2026-04-17 14:30:00', 200000, 500000, 400000, 190000, 590000, 'COMPLETED'),
('079123456792', 16, '2026-05-03 07:00:00', '2026-05-09 07:00:00', null,                  150000, 600000, 900000, 0, null,    'ACTIVE'),
('079123456793', 18, '2026-05-20 09:30:00', '2026-05-23 09:30:00', null,                  145000, 400000, 435000, 0, null,    'CREATED'),
('079123456794', 2,  '2026-03-20 09:00:00', '2026-03-21 09:00:00', null,                  220000, 300000, 220000, 0, null,    'CANCELLED');

insert into `rental_extrafee` (`rentalId`, `extraFeeTypeId`, `amount`, `description`) values
(1, 1, 20000, 'Tra xe tre 1 gio 10 phut, tinh 1 gio'),
(7, 2, 150000, 'Tray ben hong phai xe'),
(7, 7, 40000, 'Rua xe + ve sinh noi that'),
(10, 7, 30000, 'Xe ban sau chuyen di duong dai'),
(11, 1, 20000, 'Tra tre hon 2 gio, giam mot phan do thong cam'),
(11, 5, 50000, 'Muc xang thieu so voi ban giao'),
(13, 3, 250000, 'Vo guong trai khi dung xe'),
(13, 4, 120000, 'Mat mu bao hiem kem theo xe'),
(13, 1, 20000, 'Tre 4 gio 30 phut, tinh 1 muc co ban'),
(null, 8, 180000, 'Phi cuu ho custom khi xechet may luc khuya'),
(null, 2, 100000, 'Muc tray nhe theo thoa thuan giam gia');
