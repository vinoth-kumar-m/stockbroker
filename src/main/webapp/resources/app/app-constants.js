'use strict';
 
app.constant('constants', {
	menu: {
		Administrator: [{title: 'Stocks', href: '/stocks'}, {title: 'Investors', href: '/investors'}],
		Investor: [{title: 'Stocks', href: '/stocks'}, {title: 'Portfolio', href: '/portfolio'}]
	},
	url: {
		LOGIN: "dtcc/login",
		LOGOUT: "dtcc/logout",
		REGISTER_USER: "dtcc/register",
		GET_USERS: "dtcc/users",
		USER_PORTFOLIO: "dtcc/portfolio",
		SELL_STOCK: "dtcc/sellStock",
		BUY_STOCK: "dtcc/buyStock",
		GET_STOCKS: "dtcc/getStocks",
		REFRESH_STOCK_RATES: "dtcc/refresh",
		GET_STOCK_PERFORMANCE: "dtcc/stockPerformance"
	},
	role: {
		ADMINISTRATOR: 'Administrator',
		INVESTOR: 'Investor'
	}
});