# Detta sql skript skriver ut alla anställda (in charge) personer
SELECT u.ID, user_title_text.TEXT AS titel, u.GROUPID, u.FIRSTNAME, u.LASTNAME
FROM centre AS c
JOIN user AS u ON c.id = u.centreid
         LEFT OUTER JOIN listboxtextrow AS user_title_row ON user_title_row.TEXTVALUE = u.TITLE AND user_title_row.id = 'USER_TITLE_L'
         LEFT OUTER JOIN text AS user_title_text ON user_title_row.TEXTID = user_title_text.id AND user_title_text.LANGUAGEID = 'sv'
WHERE c.belongsto = 'TIO_100'
ORDER BY titel;