function makeGraph() {
    let graphSun = document.getElementById('graph_sun');
    let graphMon = document.getElementById('graph_mon');
    let graphTue = document.getElementById('graph_tue');
    let graphWed = document.getElementById('graph_wed');
    let graphThu = document.getElementById('graph_thu');
    let graphFri = document.getElementById('graph_fri');
    let graphSat = document.getElementById('graph_sat');


    const arrGraph = [graphSun, graphMon, graphTue, graphWed, graphThu, graphFri, graphSat];

    const arrAccum = []
    arrAccum[0] = [[${week_time[0]}]];
    arrAccum[1] = [[${week_time[1]}]];
    arrAccum[2] = [[${week_time[2]}]];
    arrAccum[3] = [[${week_time[3]}]];
    arrAccum[4] = [[${week_time[4]}]];
    arrAccum[5] = [[${week_time[5]}]];
    arrAccum[6] = [[${week_time[6]}]];

    let max = arrAccum[0];
    for (let i = 1; i < 7; i++) {
        if (arrAccum[i] > max) {
            max = arrAccum[i];
        }
    }
    console.log(arrAccum);

    const abs = 145 / max;

    for (let i = 0; i < 7; i++) {
        arrGraph[i].style.height = arrAccum[i] * abs + 'px';
    }
}