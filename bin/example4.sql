drop view view0;
create or replace view view0 as
select prod, month
from sales
where year=1997
group by prod, month
;

drop view view1;
create or replace view view1 as
select a.prod, a.month, avg(b.quant) as pre_avg
from view0 a, sales b
where b.year=1997 and a.prod=b.prod and a.month=b.month + 1
group by a.prod, a.month
;

drop view view2;
create or replace view view2 as
select a.prod, a.month, avg(b.quant) as fol_avg
from view0 a, sales b
where b.year=1997 and a.prod=b.prod and a.month=b.month - 1
group by a.prod, a.month
;

select sales.prod, sales.month, count(sales.*)
from sales, view1, view2
where sales.year=1997 and sales.prod=view1.prod and sales.prod=view2.prod
      and sales.month=view1.month and sales.month=view2.month
      and sales.quant>view1.pre_avg and sales.quant < view2.fol_avg
group by sales.prod, sales.month