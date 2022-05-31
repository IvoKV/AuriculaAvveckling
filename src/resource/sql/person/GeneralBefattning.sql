SELECT u.ID AS HSAID, u.FIRSTNAME, u.LASTNAME, user_title_text.TEXT AS titel
FROM user u
         LEFT OUTER JOIN listboxtextrow AS user_title_row ON user_title_row.TEXTVALUE = u.TITLE AND user_title_row.ID = 'USER_TITLE_L'
         LEFT OUTER JOIN text AS user_title_text ON user_title_row.TEXTID = user_title_text.ID AND user_title_text.LANGUAGEID = 'sv'
        JOIN centre AS c ON u.CENTREID = c.ID
WHERE c.ID = '11012AK'
;