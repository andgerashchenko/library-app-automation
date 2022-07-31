select isbn, b.name, author, bc.name, year from books b left join book_categories bc on b.book_category_id = bc.id where b.name = 'The Road Less Traveled' and author='Jerald Mitchell';

