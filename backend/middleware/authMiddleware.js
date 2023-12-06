const jwt = require('jsonwebtoken')
const dotenv = require('dotenv')
dotenv.config();

const authMiddleWare = (req, res, next) => {

    const token = req.headers.token.split(' ')[1]
    jwt.verify(token, process.env.ACCESS_TOKEN, function(err, user) {
        if(err) {
            return res.status(404).json({
                message: 'the authentication',
                status: 'error'
            })
        }
        if(user?.isAdmin) {
            console.log('true');
            next();
        } else {
            return res.status(404).json({
                message: 'the authentication',
                status: 'error'
            })
        }
        console.log('user', user);
    });
}

const authUserMiddleWare = (req, res, next) => {
    const token = req.headers.token.split(' ')[1]
    const userId = req.params.id
    jwt.verify(token, process.env.ACCESS_TOKEN, function(err, user) {
        if(err) {
            return res.status(404).json({
                message: 'the authenticationsss',
                status: 'error'
            })
        }
        if(user?.isAdmin || user?.id === userId) {
            console.log('true');
            next();
        } else {
            return res.status(404).json({
                message: 'the authentication',
                status: 'error'
            })
        }
        console.log('user', user);
    });
}


module.exports = {
    authMiddleWare,
    authUserMiddleWare
}