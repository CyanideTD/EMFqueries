drop view view1;
create view view1 as
select prod, month, sum(quant) as sum_1_quant
from sales
group by prod, month
;

drop view view2;
create view view2 as
select prod, sum(quant) as sum_2_quant
from sales
group by prod
;

select view1.prod, view1.month, cast(view1.sum_1_quant as decimal) / view2.sum_2_quant as "1_sum_quant / 2_sum_quant"
from view1, view2
where view1.prod = view2.prod;

