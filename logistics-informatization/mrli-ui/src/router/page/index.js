import Layout from '@/page/index/'

export default [{
  path: '/login',
  name: '登录页',
  component: () =>
    import(/* webpackChunkName: "page" */ '@/page/login/index'),
  meta: {
    keepAlive: true,
    isTab: false,
    isAuth: false
  }
}, {
  path: '/wel',
  component: Layout,
  redirect: '/wel/index',
  children: [{
    path: 'index',
    name: '首页',
    component: () =>
      import(/* webpackChunkName: "views" */ '@/page/wel')
  }]
},
  {
    path: '/lock',
    name: '锁屏页',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/page/lock/index'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/404',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/components/error-page/404'),
    name: '404',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: true
    }

  },
  {
    path: '/403',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/components/error-page/403'),
    name: '403',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/500',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/components/error-page/500'),
    name: '500',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/',
    name: '主页',
    redirect: '/wel'
  },
  {
    path: '/myiframe',
    component: Layout,
    redirect: '/myiframe',
    children: [{
      path: ':routerPath',
      name: 'iframe',
      component: () =>
        import(/* webpackChunkName: "page" */ '@/components/iframe/main'),
      props: true
    }]
  },
  {
    path: '*',
    redirect: '/404'
  },
  {
    path: '/authredirect',
    name: '授权页',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/page/login/authredirect'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
	{
    path: '/inoutyardbody',
    component: Layout,
     children: [{
    path: 'body',
    name: '保税物流表体',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/baoshuisystem/inoutyardbody/index'),
  }]
  },
  {
    path: '/inoutyardbodyWarehouse',
    component: Layout,
     children: [{
    path: 'body',
    name: '保税仓库表体',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/baoshuisystem/inoutyardbodyWarehouse/index'),
  }]
  },
	 {
    path: '/meeting',
    component: Layout,
     children: [{
    path: 'people',
    name: '参会者管理',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/meetingpeople/index'),
  }]
  },
  {
    path: '/tabSetting',
    component: Layout,
     children: [{
    path: '/',
    name: '签到设置标签',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/meetingmanage/tap_index'),
  }]
  },
   {
    path: '/mobile',
    name: '公众号',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/mobile/index'),
    meta: {
      title: '签到',
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/initMobile',
    name: '公众号',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/mobile/init'),
    meta: {
      title: '初始化微信',
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
   {
    path: '/bigView',
    name: '大屏',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/meetingbigview/index'),
    meta: {
      title: '签到',
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/bigViewTable',
    name: '大屏报表',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/meetingsystem/meetingbigview/index_table'),
    meta: {
      title: '签到',
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  }
  
]
