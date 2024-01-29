# Entities 

## User
- name
- surname
- email
- userName
- pasword
- photo
- banner
- about
- role

## Author
- name
- surname
- photo

## Category
- name

## Publisher
- name

## Book
- isbn
- description
- name
- page
- image
- rate
- authorId
- categoryId
- publisherId

## UserBookCollection
- name
- userId
  
## UserBookCollectionItem
- userBookCollectionId
- bookId


## UserBookRate
- userId
- bookId
- content?
- rate
- like
- dislike

## UserBookRateLike
- userBookRateId
- userId
- type (like,dislike)


## UserReadBook
- userId
- bookId
- date