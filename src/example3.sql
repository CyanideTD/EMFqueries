create or replace view view0 as
select cust, month, avg (quant) as avg_quant
from sales
group by cust, month
;

drop view view1;
create or replace view view1 as
select a.cust, a.month, avg(b.quant) as pre_avg_quant
from view0 a, sales b 
where a.cust = b.cust
      and a.month > b.month
group by a.cust, a.month
;



drop view view2;
create or replace view view2 as
select a.cust, a.month, avg(b.quant) as aft_avg_quant
from view0 a, sales b
where and a.cust = b.cust
      and a.month < b.month
group by a.cust, a.month
;

select view0.cust, view0.month, view1.pre_avg_quant as "1_avg_quant", view0.avg_quant as "0_avg_quant", view2.aft_avg_quant as "2_avg_quant"
from view0, view1, view2
where view0.cust=view1.cust and view0.cust=view2.cust and view0.month=view1.month and view0.month=view2.month
