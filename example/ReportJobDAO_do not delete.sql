-- #[GET_ALL_AD_GROUP_KEYWORD_DAILY_SPENDING_BY_YESTERDAY]:
SELECT
		TMP.ad_group_keyword_id,
		TMP.`day`,
		TMP.sum_impression_count,
		TMP.sum_click_count,
		COUNT(A.id) as sum_arrival_count,
		TMP.sum_total_spending,
		TMP.weight_avg_position,
		TMP.weight_quality_score
FROM
    (SELECT AGKP.ad_group_keyword_id, DATE(AGKP.start_at) as `day`,
		AGKP.impression_count as sum_impression_count,
		AGKP.click_count as sum_click_count,
		AGKP.total_spending as sum_total_spending,
		AGKP.avg_position as weight_avg_position,
		AGKP.quality_score as weight_quality_score
		FROM Ad_Group_Keyword_Performance as AGKP
		WHERE AGKP.updated_ts BETWEEN ? AND ?) as TMP
JOIN
		Arrival_Property as AP
ON
		(TMP.ad_group_keyword_id = AP.`value`
		AND AP.`name` = 'ad_group_keyword_id')
JOIN
		Arrival as A
ON
		(AP.arrival_id = A.id
		AND DATE(A.created_ts) = TMP.`day`);

-- #[GET_ALL_AD_GROUP_KEYWORD_DAILY_REVENUE_BY_YESTERDAY]:
SELECT
		Lead_Tmp.ad_group_keyword_id,
		Lead_Tmp.`day`,
		SUM(Lead_Tmp.lead_count) as lead_count,
		SUM(Lead_Tmp.total_lead_revenue) as total_lead_revenue,
		SUM(Impression_Tmp.impression_count) as ad_impression_count,
		SUM(Click_Tmp.click_count) as ad_click_count,
		SUM(Click_Tmp.click_revenue) as total_ad_click_revenue,
		COUNT(CASE WHEN (Lead_Tmp.conversion_count || Click_Tmp.conversion_count) THEN 1 ELSE 0 END) as conversion_count
FROM
		(SELECT
		AGK.id as ad_group_keyword_id, DATE(A.created_ts) as `day`, A.id as arrival_id,
		COUNT(LS.id) as lead_count,
		SUM(LS.amount) as total_lead_revenue,
		(CASE WHEN COUNT(L.id) > 0 THEN 1 ELSE 0 END) as conversion_count
		FROM Ad_Group_Keyword as AGK
		JOIN Arrival_Property as AP
		ON (AGK.id = AP.`value`
		AND AP.`name` = 'ad_group_keyword_id')
		JOIN Arrival as A
		ON AP.arrival_id = A.id
		JOIN Lead as L
		ON A.id = L.arrival_id
		JOIN Lead_Sell as LS
		ON (L.id = LS.lead_id
		AND LS.sell_state = 'sold'
		AND LS.updated_ts BETWEEN ? AND ?)
		GROUP BY 1, 2, 3) as Lead_Tmp
JOIN
		(SELECT AGK.id as ad_group_keyword_id, DATE(A.created_ts) as `day`, A.id as arrival_id,
		COUNT(CO.id) as click_count,
		SUM(CASE WHEN adjusted_amount IS NOT NULL THEN adjusted_amount ELSE amount END) as click_revenue,
		(CASE WHEN COUNT(CO.id) > 0 THEN 1 ELSE 0 END) as conversion_count
		FROM Ad_Group_Keyword as AGK
		JOIN Arrival_Property as AP
		ON (AGK.id = AP.`value`
		AND AP.`name` = 'ad_group_keyword_id')
		JOIN Arrival as A
		ON AP.arrival_id = A.id
		JOIN Click_Impression_Request as CIR
		ON A.id = CIR.arrival_id
		JOIN Click_Impression as CI
		ON CIR.id = CI.click_impression_request_id
		JOIN Click_Out as CO
		ON (CI.id = CO.click_impression_id
		AND CO.updated_ts BETWEEN ? AND ?)
		GROUP BY 1, 2, 3) as Click_Tmp
ON
		(Lead_Tmp.ad_group_keyword_id = Click_Tmp.ad_group_keyword_id
		AND Lead_Tmp.`day` = Click_Tmp.`day`
		AND Lead_Tmp.arrival_id = Click_Tmp.arrival_id)
JOIN
		(SELECT AGK.id as ad_group_keyword_id, DATE(A.created_ts) as `day`, A.id as arrival_id,
		COUNT(CI.id) as impression_count
		FROM Ad_Group_Keyword as AGK
		JOIN Arrival_Property as AP
		ON (AGK.id = AP.`value`
		AND AP.`name` = 'ad_group_keyword_id')
		JOIN Arrival as A
		ON AP.arrival_id = A.id
		JOIN Click_Impression_Request as CIR
		ON A.id = CIR.arrival_id
		JOIN Click_Impression as CI
		ON CIR.id = CI.click_impression_request_id
		GROUP BY 1, 2, 3) as Impression_Tmp
ON
		(Lead_Tmp.ad_group_keyword_id = Impression_Tmp.ad_group_keyword_id
		AND Lead_Tmp.`day` = Impression_Tmp.`day`
		AND Lead_Tmp.arrival_id = Impression_Tmp.arrival_id)
GROUP BY 1, 2;