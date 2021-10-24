//const mockFetch = require('./mock-fetch')
const successResponse = [
    {
        'userId': '1',
        "data": 'This looks slick!'
    },
    {
        'userId': '2',
        "data": 'I think this can be improved.'
    },
    {
        'userId': '1',
        "data": 'What kind of improvement?'
    }];
//mockFetch('/api/comments', successResponse);

async function getCommentsByUserId(userId) {
    let arr=[];
    for(let i=0;i<successResponse.length;i++)
    {
        if(successResponse[i].userId===userId)
        {
            arr.push(successResponse[i].data)
        }
    }
    return arr
}

//module.exports = getCommentsByUserId;

console.log(getCommentsByUserId('1'));
