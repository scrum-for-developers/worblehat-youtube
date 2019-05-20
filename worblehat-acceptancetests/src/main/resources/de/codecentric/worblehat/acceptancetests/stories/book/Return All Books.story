Meta:
@themes Book

Narrative:
In order to give back borrowed books
As a borrower
I want to return all my borrowed books at once

Scenario:
Given an empty library
And borrower <borrower1> has borrowed books <isbns1>
And borrower <borrower2> has borrowed books <isbns2>
When borrower <borrower1> returns all his books
Then books <isbns1> are not borrowed anymore by borrower <borrower1>
And books <isbns2> are still borrowed by borrower <borrower2>

Examples:    
    
| borrower1       | isbns1                 | borrower2       | isbns2                 |
| user1@dings.com | 0321293533             |                 |                        |
| user1@dings.com | 0321293533             | user2@dings.com | 1234567962             |
| user1@dings.com | 0321293533, 1234567962 |                 |                        |
| user1@dings.com | 0321293533, 1234567962 | user2@dings.com | 7784484156, 1126108624 |


Scenario: Returning books ignores whitespaces
Given an empty library
And borrower test@me.com has borrowed books 1234567962
!-- Note the whitespace at the start and end of parameters in the following step
When borrower       test@me.com        returns all his books
Then books 1234567962 are not borrowed anymore by borrower test@me.com
