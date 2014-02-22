Meta:
@themes Book

Narrative:
In order to give back borrowed books
As a borrower
I want to return all my borrowed books at once

Scenario:

Given an empty library
And a user <user> has borrowed books <isbns>
And a user <user2> has borrowed books <isbns2>

When user <user> returns all his books

Then books <isbns> are not borrowed anymore by user <user>
And books <isbns2> are still borrowed by user <user2>

Examples:    
    
| user            | isbns                 | user2           | isbns2                |
| user1@dings.com | 0321293533            |                 |                       |
| user1@dings.com | 0321293533            | user2@dings.com | 1234567962            |
| user1@dings.com | 0321293533            | user2@dings.com | 0321293533            |
| user1@dings.com | 0321293533 1234567962 |                 |                       |
| user1@dings.com | 0321293533 1234567962 | user2@dings.com | 1234567962            |
| user1@dings.com | 0321293533 1234567962 | user2@dings.com | 0321293533 1234567962 |









