drop view view1;
create view view1 as
select cust, avg(quant) as avg_1_quant
from sales
where state='NY'
group by cust
;

drop view view2;
create view view2 as
select cust, avg(quant) as avg_2_quant
from sales
where state='CT'
group by cust
;

drop view view3;
create view view3 as
select cust, avg(quant) as avg_3_quant
from sales
where  state='NJ'
group by cust
;

select view1.cust, view1.avg_1_quant as "1_avg_quant", view2.avg_2_quant as "2_avg_quant", view2.avg_2_quant as "3_avg_quant" 
from view1, view2, view3
where view1.cust=view2.cust and view2.cust=view3.cust
      and view1.avg_1_quant > view2.avg_2_quant and view1.avg_1_quant > view3.avg_3_quant
;

