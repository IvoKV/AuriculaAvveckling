select u.ID, user_title_text.TEXT as titel, u.GROUPID, u.FIRSTNAME, u.LASTNAME from user u
    left outer join listboxtextrow user_title_row on user_title_row.TEXTVALUE = u.TITLE and user_title_row.id = 'USER_TITLE_L'
    left outer join text user_title_text on user_title_row.TEXTID = user_title_text.ID and user_title_text.LANGUAGEID = 'sv'
GROUP BY user_title_text.TEXT;