const express = require('express')
const cors = require('cors')
const fs = require('fs').promises
const utils = require('./utils')

const app = express()

app.use(cors())

let clock1Time, clock2Time

async function bootstrap() {
    clock1Time = clock2Time = -1
    await utils.saveTimes(clock1Time, clock2Time)
}

app.get('/setup-timer', async (req, res, next) => {
    clock1Time = +(req.query.clock1)
    clock2Time = +(req.query.clock2)
    console.log(req.query)

    await utils.saveTimes(clock1Time, clock2Time)

    await utils.startTimer(clock1Time + clock2Time)

    await bootstrap()

    return res.json({
        code: 200
    })
})

app.get('/get-times', async (req, res, next) => {
    const times = await utils.getTimes()
    return res.json(times)
})


app.listen(3000, () => {
    bootstrap()
    console.log('Server running on port 3000')
})