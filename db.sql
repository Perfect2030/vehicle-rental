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
('59B2-34567', 'Exciter 155', 'Yamaha', 'MANUAL', 155, 250000, 'MAINTENANCE'),
('59B2-45678', 'Sirius FI', 'Yamaha', 'AUTOMATIC', 113, 130000, 'AVAILABLE'),
('59C3-56789', 'Lead 125', 'Honda', 'SCOOTER', 125, 180000, 'AVAILABLE'),
('59C3-67890', 'Janus', 'Yamaha', 'SCOOTER', 125, 140000, 'MAINTENANCE'),
('59D4-11111', 'SH Mode', 'Honda', 'SCOOTER', 125, 280000, 'MAINTENANCE'),
('59D4-22222', 'Wave Alpha', 'Honda', 'AUTOMATIC', 100, 120000, 'AVAILABLE'),
('59E5-33333', 'NVX 155', 'Yamaha', 'SCOOTER', 155, 230000, 'AVAILABLE'),
('59E5-44444', 'Winner X', 'Honda', 'MANUAL', 150, 240000, 'MAINTENANCE'),
('59F6-55555', 'Grande', 'Yamaha', 'SCOOTER', 125, 170000, 'AVAILABLE'),
('59F6-66666', 'Vario 160', 'Honda', 'SCOOTER', 160, 260000, 'AVAILABLE'),
('59G7-77777', 'SH 125i', 'Honda', 'SCOOTER', 125, 320000, 'MAINTENANCE'),
('59G7-88888', 'Future 125', 'Honda', 'AUTOMATIC', 125, 160000, 'AVAILABLE'),
('59H8-99999', 'PG-1', 'Yamaha', 'AUTOMATIC', 150, 200000, 'AVAILABLE'),
('59H8-12121', 'FreeGo', 'Yamaha', 'SCOOTER', 125, 150000, 'MAINTENANCE'),
('59K9-34343', 'Blade 110', 'Honda', 'AUTOMATIC', 110, 110000, 'AVAILABLE'),
('59K9-56565', 'Jupiter', 'Yamaha', 'AUTOMATIC', 135, 145000, 'AVAILABLE'),
('59L0-78787', 'Air Blade 125', 'Honda', 'SCOOTER', 125, 190000, 'AVAILABLE'),
('59L0-90909', 'Exciter 135', 'Yamaha', 'MANUAL', 135, 210000, 'AVAILABLE'),
('59M1-10101', 'Vision 110', 'Honda', 'SCOOTER', 110, 140000, 'AVAILABLE'),
('59M1-20202', 'Janus 125', 'Yamaha', 'SCOOTER', 125, 160000, 'AVAILABLE'),
('59N2-30303', 'Click 125', 'Honda', 'AUTOMATIC', 125, 155000, 'AVAILABLE'),
('59N2-40404', 'Luvias Fi', 'Yamaha', 'SCOOTER', 125, 165000, 'AVAILABLE'),
('59P3-50505', 'Scoopy 110', 'Honda', 'AUTOMATIC', 110, 125000, 'AVAILABLE'),
('59P3-60606', 'Filano', 'Yamaha', 'SCOOTER', 125, 145000, 'AVAILABLE'),
('59Q4-70707', 'PCX 160', 'Honda', 'SCOOTER', 160, 275000, 'AVAILABLE'),
('59Q4-80808', 'Aerox 155', 'Yamaha', 'SCOOTER', 155, 240000, 'AVAILABLE'),
('59R5-90909', 'Forza 300', 'Honda', 'AUTOMATIC', 300, 450000, 'AVAILABLE'),
('59R5-01010', 'T-Max', 'Yamaha', 'AUTOMATIC', 530, 500000, 'AVAILABLE'),
('59S6-11111', 'CB500F', 'Honda', 'MANUAL', 500, 480000, 'AVAILABLE'),
('59S6-21212', 'MT-07', 'Yamaha', 'MANUAL', 700, 520000, 'AVAILABLE'),
('59T7-31313', 'SH 150i', 'Honda', 'SCOOTER', 150, 310000, 'AVAILABLE'),
('59T7-41414', 'NMax 155', 'Yamaha', 'SCOOTER', 155, 290000, 'AVAILABLE'),
('59U8-51515', 'Vario 125', 'Honda', 'SCOOTER', 125, 175000, 'AVAILABLE'),
('59U8-61616', 'Mio i125', 'Yamaha', 'AUTOMATIC', 125, 138000, 'AVAILABLE'),
('59V9-71717', 'Beat 110', 'Honda', 'AUTOMATIC', 110, 115000, 'AVAILABLE'),
('59V9-81818', 'Crypton 110', 'Yamaha', 'AUTOMATIC', 110, 118000, 'AVAILABLE'),
('59W0-91919', 'Repsol CBR', 'Honda', 'MANUAL', 150, 320000, 'AVAILABLE'),
('59W0-02020', 'YZF-R15', 'Yamaha', 'MANUAL', 155, 340000, 'AVAILABLE'),
('59X1-12121', 'XSR125', 'Yamaha', 'MANUAL', 125, 280000, 'AVAILABLE'),
('59X1-22222', 'CB150R', 'Honda', 'MANUAL', 150, 295000, 'AVAILABLE'),
('59Y2-32323', 'Jupiter MX 135', 'Yamaha', 'AUTOMATIC', 135, 155000, 'AVAILABLE'),
('59Y2-42424', 'RS150R', 'Yamaha', 'MANUAL', 150, 265000, 'AVAILABLE'),
('59Z3-52525', 'XR150L', 'Honda', 'MANUAL', 150, 275000, 'AVAILABLE'),
('59Z3-62626', 'WR155R', 'Yamaha', 'MANUAL', 155, 285000, 'AVAILABLE'),
('59AA-72727', 'CB190R', 'Honda', 'MANUAL', 190, 350000, 'AVAILABLE'),
('59AA-82828', 'YZF-R3', 'Yamaha', 'MANUAL', 321, 420000, 'AVAILABLE'),
('59BB-92929', 'MSX125', 'Honda', 'AUTOMATIC', 125, 145000, 'AVAILABLE'),
('59BB-03030', 'Lexi 125', 'Yamaha', 'SCOOTER', 125, 155000, 'AVAILABLE'),
('59CC-13131', 'Rebel 500', 'Honda', 'MANUAL', 471, 480000, 'AVAILABLE');


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
('079123456800', 'Truong My Oanh', '0357234567', 'Quan 6, TP.HCM'),
('079123456801', 'Nguyen Thi Thao', '0346345678', 'Tan Phu, TP.HCM'),
('079123456802', 'Tran Van Binh', '0335456789', 'Nhan Chinh, Ha Noi'),
('079123456803', 'Le Thi Giang', '0324567890', 'Cau Giay, Ha Noi'),
('079123456804', 'Pham Quang Huy', '0313678901', 'Thanh Xuan, Ha Noi'),
('079123456805', 'Hoang Van Kien', '0902789012', 'Dong Da, Ha Noi'),
('079123456806', 'Vo Thi Linh', '0891890123', 'Hai Ba Trung, Ha Noi'),
('079123456807', 'Bui Van Minh', '0880901234', 'Ba Dinh, Ha Noi'),
('079123456808', 'Dang Thi Nhan', '0869012345', 'Hoan Kiem, Ha Noi'),
('079123456809', 'Do Quang Oanh', '0858123456', 'Da Nang City'),
('079123456810', 'Mai Thi Phuong', '0847234567', 'Hai Chau, Da Nang'),
('079123456811', 'Phan Van Quoc', '0836345678', 'Thanh Khe, Da Nang'),
('079123456812', 'Truong Thi Hoa', '0825456789', 'Ngu Hanh Son, Da Nang'),
('079123456813', 'Nguyen Van Quy', '0814567890', 'Can Tho City');

insert into `extrafee_type` (`name`, `amount`, `description`) values
('Trả xe trễ theo giờ', 20000, 'Phạt mỗi giờ trả trễ'),
('Trầy xe nhẹ', 150000, 'Vết trầy nhỏ trên vỏ xe'),
('Vỡ gương', 250000, 'Hỏng động một gương chiếu hậu'),
('Mất mũ bảo hiểm', 120000, 'Khách làm mất mũ bảo hiểm đi kèm'),
('Thiếu xăng khi trả', 50000, 'Mức xăng thấp hơn lúc nhận xe'),
('Hỏng khóa tự', 300000, 'Khóa tự gãy hoặc kẹt'),
('Rửa xe', 30000, 'Phí vệ sinh khi xe quá bẩn'),
('Cứu hộ ngoài khu vực', 200000, 'Hỗ trợ cứu hộ xa trung tâm'),
('Trầy xe nặng', 500000, 'Hỏng hoàn toàn cả xe'),
('Gập kệ nhất', 180000, 'Hỏng động kệ nhất hoặc kệ bên');