SELECT newColor, count(*) as count
FROM pixelupdates
WHERE updatedAt not in (select firstPaintedAt from pixels)
group by newColor
order by count desc;
