create table inv_invoice (
	id binary(16) not null,
	canceled_date datetime,
	code varchar(20),
	confirmed_by_id varchar(50),
	confirmed_by_name varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	due_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	receive_address_detail varchar(50),
	receive_address_postal_code varchar(10),
	receive_address_street varchar(50),
	received_date datetime,
	receiver_id varchar(50),
	remark varchar(50),
	status varchar(20),
	supplier_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table inv_invoice_item (
	id binary(16) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	invoice_id binary(16),
	item_id binary(16),
	item_lot_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	quantity decimal(19,2),
	remark varchar(50),
	unit varchar(20),
	primary key (id)
) engine=InnoDB;

create index IDXs03yl06gam490pyaradqehywb
	on inv_invoice_item (invoice_id);
