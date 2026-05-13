drop database if exists vehicle_rental;
create database vehicle_rental;
use vehicle_rental;

create table `vehicle` (
    `id` int auto_increment primary key,
    `licensePlate` varchar(20) not null,
    `model` varchar(100),
    `brand` varchar(50),
    `vehicleType` ENUM ('MANUAL', 'AUTOMATIC', 'SCOOTER') not null,
    -- MANUAL: xe côn tay, AUTOMATIC: xe số , SCOOTER: xe tay ga
    `displacement` int not null, -- phân khối (cc)
    
    `pricePerDay` int not null,
    
    `status` ENUM ('AVAILABLE', 'RENTED', 'MAINTENANCE') default 'AVAILABLE',
    
    `createdAt` datetime default current_timestamp,
    `isDeleted` boolean default false
);

create table `customer` ( 
    `id` int auto_increment primary key,
    `identityNumber` varchar(20) null,
    `fullName` varchar(100) not null,
    `phoneNumber` varchar(15),
    
    `address` varchar(255),
    
    `createdAt` datetime default current_timestamp,
    `isDeleted` boolean default false
);

create table `rental` (
    `id` int auto_increment primary key,
    
    `customerId` int not null,
    `vehicleId` int not null,
    
    `startTime` datetime not null,
    `expectedReturnTime` datetime not null,
    `actualReturnTime` datetime null,
    
    `pricePerDay` int not null,
    
    `deposit` int default 0, --
    
    `estimatedTotal` int, -- giá dự kiến
    `extraFee` int default 0, --
    `totalAmount` int,    -- giá thực tế
    
    `status` ENUM('CREATED', 'ACTIVE', 'COMPLETED', 'CANCELLED') default 'CREATED',
    
    `createdAt` datetime default current_timestamp,
    
    foreign key (`customerId`) references `customer`(`id`),
    foreign key (`vehicleId`) references `vehicle`(`id`)
);

create table `extrafee_type` (
    `id` int auto_increment primary key,
    `name` varchar(100) unique,       -- Trầy xe, Vỡ gương...
    `amount` int not null,          -- Số tiền phạt tiêu chuẩn cho loại phí này
    `description` varchar(255)
);

create table `rental_extrafee` (
    `id` int auto_increment primary key,
    `rentalId` int null,

    `name` varchar(100) not null, -- Tên loại phí (có thể lấy từ extrafee_type hoặc nhập trực tiếp nếu có thỏa thuận riêng)
    `amount` int not null, -- Số tiền thực tế phải trả cho loại phí này (có thể khác với amount trong ExtraFeeType nếu có thỏa thuận riêng)
    `description` varchar(255), -- Mô tả chi tiết về phí này (ví dụ: "Trầy xe do khách hàng không cẩn thận")
    
    foreign key (`rentalId`) references `rental`(`id`)
);

-- =========================
-- Seed data for testing
-- =========================

insert into `vehicle` (`licensePlate`, `model`, `brand`, `vehicleType`, `displacement`, `pricePerDay`, `status`) values
('59A1-12345', 'Vision 2024', 'Honda', 'SCOOTER', 110, 150000, 'AVAILABLE'),
('59A1-23456', 'Air Blade 160', 'Honda', 'SCOOTER', 160, 220000, 'AVAILABLE'),
('59B2-34567', 'Exciter 155', 'Yamaha', 'MANUAL', 155, 250000, 'RENTED'),
('59B2-45678', 'Sirius FI', 'Yamaha', 'AUTOMATIC', 113, 130000, 'AVAILABLE'),
('59C3-56789', 'Lead 125', 'Honda', 'SCOOTER', 125, 180000, 'AVAILABLE'),
('59C3-67890', 'Janus', 'Yamaha', 'SCOOTER', 125, 140000, 'RENTED'),
('59D4-11111', 'SH Mode', 'Honda', 'SCOOTER', 125, 280000, 'MAINTENANCE'),
('59D4-22222', 'Wave Alpha', 'Honda', 'AUTOMATIC', 100, 120000, 'AVAILABLE'),
('59E5-33333', 'NVX 155', 'Yamaha', 'SCOOTER', 155, 230000, 'AVAILABLE'),
('59E5-44444', 'Winner X', 'Honda', 'MANUAL', 150, 240000, 'RENTED'),
('59F6-55555', 'Grande', 'Yamaha', 'SCOOTER', 125, 170000, 'AVAILABLE'),
('59F6-66666', 'Vario 160', 'Honda', 'SCOOTER', 160, 260000, 'AVAILABLE'),
('59G7-77777', 'SH 125i', 'Honda', 'SCOOTER', 125, 320000, 'MAINTENANCE'),
('59G7-88888', 'Future 125', 'Honda', 'AUTOMATIC', 125, 160000, 'AVAILABLE'),
('59H8-99999', 'PG-1', 'Yamaha', 'AUTOMATIC', 150, 200000, 'AVAILABLE'),
('59H8-12121', 'FreeGo', 'Yamaha', 'SCOOTER', 125, 150000, 'RENTED'),
('59K9-34343', 'Blade 110', 'Honda', 'AUTOMATIC', 110, 110000, 'AVAILABLE'),
('59K9-56565', 'Jupiter', 'Yamaha', 'AUTOMATIC', 135, 145000, 'AVAILABLE');

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
(1, 1,  '2026-03-01 08:00:00', '2026-03-03 08:00:00', '2026-03-03 09:10:00', 150000, 500000, 300000, 20000, 320000, 'COMPLETED'),
(2, 2,  '2026-03-05 09:00:00', '2026-03-06 09:00:00', '2026-03-06 08:40:00', 220000, 500000, 220000, 0, 220000, 'COMPLETED'),
(3, 3,  '2026-05-05 10:00:00', '2026-05-07 10:00:00', null,                  250000, 700000, 500000, 0, null,    'ACTIVE'),
(4, 8,  '2026-05-10 07:30:00', '2026-05-12 07:30:00', null,                  120000, 400000, 240000, 0, null,    'CREATED'),
(5, 5,  '2026-04-10 14:00:00', '2026-04-12 14:00:00', null,                  180000, 500000, 360000, 0, null,    'CANCELLED'),
(6, 6,  '2026-05-04 16:00:00', '2026-05-06 16:00:00', null,                  140000, 400000, 280000, 0, null,    'ACTIVE'),
(7, 4,  '2026-02-20 09:00:00', '2026-02-22 09:00:00', '2026-02-22 12:30:00', 130000, 300000, 260000, 190000, 450000, 'COMPLETED'),
(9, 7,  '2026-03-15 08:00:00', '2026-03-17 08:00:00', null,                  30000, 100000, 60000, 0, null,    'ACTIVE'),
(9, 9,  '2026-04-01 10:00:00', '2026-04-03 10:00:00', null,                  230000, 500000, 460000, 0, null,    'CREATED'),
(10, 10, '2026-05-01 09:00:00', '2026-05-03 09:00:00', '2026-05-03 18:30:00', 240000, 600000, 480000, 30000, 510000, 'COMPLETED');

insert into `rental_extrafee` (`rentalId`, `name`, `amount`, `description`) values
(1, 'Tra xe tre theo gio', 20000, 'Tra xe tre 1 gio 10 phut, tinh 1 gio'),
(7, 'Tray xe nhe', 150000, 'Tray ben hong phai xe'),
(7, 'Rua xe', 40000, 'Rua xe + ve sinh noi that');