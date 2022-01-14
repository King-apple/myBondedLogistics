import request from '@/router/axios'

 

export function fetchList(query) {
    return request({
        url: '/dispatch/sendworkman/page',
        method: 'get',
        params: query
    })
}

export function getWorkSend(query) {
    return request({
        url: '/dispatch/workersend/page',
        method: 'get',
        params: query
    })
} 
export function getWorkClass(query) {
    return request({
        url: '/dispatch/workclass/page',
        method: 'get',
        params: query
    })
} 
export function getClassNum(query) {
    return request({
        url: '/dispatch/workclassnum/page',
        method: 'get',
        params: query
    })
}  
export function getDay(query) {
    return request({
        url: '/dispatch/sendworkman/page',
        method: 'get',
        params: query
    })
}  
export function getTrain(query) {
    return request({
        url: '/dispatch/worktrain/page',
        method: 'get',
        params: query
    })
}  

export function addgr(send,day,train) {
    return request({
        url: '/dispatch/sendworkman/addgr',
        method: 'post',
        data: { sendWork: send,dayRecord:day,workTrain:train }
    }) 
}

export function delgr( wark ) {
    return request({
        url: '/dispatch/sendworkman/delgr',
        method: 'post',
        data: { workClass: wark  }
    }) 
}

export function goback(query) {
    return request({
        url: '/dispatch/sendworkman/goback',
        method: 'post',
        params: query
    })
} 