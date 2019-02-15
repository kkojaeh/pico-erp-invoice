ALTER TABLE inv_invoice_item CHANGE item_lot_id item_spec_code varchar(20);

create table inv_invoice_item_lot (
	id binary(16) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	invoice_item_id binary(16),
	item_lot_code varchar(20),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	quantity decimal(19,2),
	primary key (id)
) engine=InnoDB;

create index IDXdorrs5wisbqpg6pmn0ojrxu6x
	on inv_invoice_item_lot (invoice_item_id);
