ALTER TABLE Campaign_Budget CHANGE amount temp_amount decimal(10, 2);
ALTER TABLE Campaign_Budget ADD COLUMN amount INT NOT NULL AFTER temp_amount;
UPDATE Campaign_Budget SET amount = FLOOR((temp_amount+0.0001)*100);
ALTER TABLE Campaign_Budget DROP COLUMN temp_amount;

ALTER TABLE Ad_Group_Keyword CHANGE bid_amount temp_bid_amount decimal(10, 2);
ALTER TABLE Ad_Group_Keyword ADD COLUMN bid_amount INT NULL DEFAULT NULL AFTER temp_bid_amount;
UPDATE Ad_Group_Keyword SET bid_amount = FLOOR((temp_bid_amount+0.0001)*100);
ALTER TABLE Ad_Group_Keyword DROP COLUMN temp_bid_amount;

ALTER TABLE Google_New_Keyword CHANGE bid_amount temp_bid_amount decimal(10, 2);
ALTER TABLE Google_New_Keyword ADD COLUMN bid_amount INT NULL DEFAULT NULL AFTER temp_bid_amount;
UPDATE Google_New_Keyword SET bid_amount = FLOOR((temp_bid_amount+0.0001)*100);
ALTER TABLE Google_New_Keyword DROP COLUMN temp_bid_amount;

ALTER TABLE Ad_Group_Keyword_Daily_Spending CHANGE total_spending temp_total_spending decimal(10, 2);
ALTER TABLE Ad_Group_Keyword_Daily_Spending ADD COLUMN total_spending LONG NULL DEFAULT NULL AFTER temp_total_spending;
UPDATE Ad_Group_Keyword_Daily_Spending SET total_spending = FLOOR((temp_total_spending+0.0001)*100);
ALTER TABLE Ad_Group_Keyword_Daily_Spending DROP COLUMN temp_total_spending;