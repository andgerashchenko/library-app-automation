select isbn, b.name, author, bc.name, year from books b left join book_categories
    bc on b.book_category_id = bc.id where b.name = 'The Road Less Traveled' and author='Jerald Mitchell';

select * from book_borrow where is_returned=0;

select count(distinct id) from users;
select * from book_categories;


select b.name
from book_borrow br inner join books b on br.book_id = b.id where b.name = 'Head First Java';

select * from book_borrow
select * from books