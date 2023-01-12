const startStopBtn = document.getElementById("circle")
const remainTime = document.getElementById("remainTime")
const restTimeLabel = document.getElementById("restTimeLabel")

let pomoInterval;
let restInterval;
let pomoTime = 1800;
let restTime = 300;
let status = 0;
let lastTime = 1800;
let email = 'kun@naver.com'

startStopBtn.addEventListener("click", isRunning);
startStopBtn.addEventListener("click", pomoStart);

function numberToTime(pomoTime) {
    let minutes = String(Math.floor(pomoTime / 60))
    const seconds = String(pomoTime % 60).padStart(2, "0");
    const hours = String(Math.floor(minutes / 60)).padStart(2, "0")

    minutes = String(minutes % 60).padStart(2, "0")

    return remainTime.innerHTML = `${hours} : ${minutes} : ${seconds}`;
}

function pomoStart() {

    if (isRunning()) {
        clearInterval(pomoInterval);
        status = 0;
        // document.body.style.backgroundColor = "#0a58ca"
        startStopBtn.style.backgroundColor = "#3b0003";
        // startStopBtn.style.borderColor = "white"

    } else {
        pomoInterval = setInterval(pomodoro, 1000);
        status = 1;
        startStopBtn.style.backgroundColor = "#002109";
        // startStopBtn.style.borderColor = "#2a2a2a"
        document.body.style.backgroundColor = "black";

    }
    console.log(isRunning())
}

function isRunning() {
    // 처음 실행할 때
    if (status === 0) {
        return false
    } else {
        return true
    }
}

function pomodoro() {

    pomoTime -= 1;
    remainTime.innerHTML = numberToTime(pomoTime)

    if (pomoTime <= 0) {
        stopTimer()

        beep()
    }
}

function stopTimer() {
    if (pomoInterval !== null) {

        // 타이머 종료
        clearInterval(pomoInterval);

        // 타이머 시간 => 마지막 세팅 시간으로 초기화
        pomoTime = lastTime;
        remainTime.innerHTML = numberToTime(pomoTime);
        changeStopColor()

        status = 0

        // 서버에 측정한 시간 전송
        const request = new XMLHttpRequest();

        request.open('post', '/basic/save-time','true');
        request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        request.send('time=' + pomoTime);
    }
}

function changeStopColor() {
    document.body.style.backgroundColor = "#0a58ca"
    startStopBtn.style.backgroundColor = "#0a58ca";
    startStopBtn.style.borderColor = "white"
}

function restStart() {
    const restMinutes = String(Math.floor(restTime / 60)).padStart(2, "0");
    const restSeconds = String(restTime % 60).padStart(2, "0");
    restTime -= 1;
    pomoBtn.innerHTML = `${restMinutes} : ${restSeconds}`;
    if (restTime < 5 && restTime >= 0) {
        beep();
    } else if (restTime < 0) {
        clearInterval(restInterval);
        window.location.reload(true);
    }
}

function beep() {
    var snd = new Audio("https://t1.daumcdn.net/cfile/tistory/99412B355CF6B93806?original");
    snd.play();
}

function getSelectedTime() {
    const setPomoTimeList = document.getElementsByName('setPomoTime');
    setPomoTimeList.forEach((node) => {
        if (node.checked) {
            pomoTime = node.value * 60
            lastTime = pomoTime

            remainTime.innerHTML = numberToTime(pomoTime);
        }
    })

    const setRestTimeList = document.getElementsByName('setRestTime');
    setRestTimeList.forEach((node) => {
        if (node.checked) {
            restTime = node.value
            restTimeLabel.innerHTML = restTime
        }
    })
}