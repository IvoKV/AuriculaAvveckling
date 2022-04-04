# Detta sql script skriver ut alla befintliga befattningar i Auricula db
SELECT user_title_text.TEXT AS titel
FROM user u
    LEFT OUTER JOIN listboxtextrow user_title_row ON user_title_row.TEXTVALUE = u.TITLE AND user_title_row.id = 'USER_TITLE_L'
    LEFT OUTER JOIN text user_title_text ON user_title_row.TEXTID = user_title_text.id AND user_title_text.LANGUAGEID = 'sv'
GROUP BY user_title_text.TEXT;