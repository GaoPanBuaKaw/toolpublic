package com.crfchina.gate.in.util;

import java.text.NumberFormat; 
import java.text.ParseException; 
 
public class ExcelPPMTandIPMTCalcUtil { 
        private static final NumberFormat nfPercent; 
        private static final NumberFormat nfCurrency; 
 
        static { 
                // establish percentage formatter. 
                nfPercent = NumberFormat.getPercentInstance(); 
                nfPercent.setMinimumFractionDigits(2); 
                nfPercent.setMaximumFractionDigits(4); 
 
                // establish currency formatter. 
                nfCurrency = NumberFormat.getCurrencyInstance(); 
                nfCurrency.setMinimumFractionDigits(2); 
                nfCurrency.setMaximumFractionDigits(2); 
        } 
 
        /** 
     * Format passed number value to appropriate monetary string for display. 
     *  
     * @param number 
     * @return localized currency string (e.g., "$1,092.20"). 
     */ 
        public static String formatCurrency(double number) { 
                return nfCurrency.format(number); 
        } 
 
        /** 
     * Format passed number value to percent string for display. 
     *  
     * @param number 
     * @return percentage string (e.g., "7.00%"). 
     */ 
        public static String formatPercent(double number) { 
                return nfPercent.format(number); 
        } 
 
        /** 
     * Convert passed string to numerical percent for use in calculations. 
     *  
     * @param s 
     * @return <code>double</code> representing percentage as a decimal. 
     * @throws ParseException 
     *             if string is not a valid representation of a percent. 
     */ 
        public static double stringToPercent(String s) throws ParseException { 
                return nfPercent.parse(s).doubleValue(); 
        } 
 
        /** 
     * Emulates Excel/Calc's PMT(interest_rate, number_payments, PV, FV, Type) 
     * function, which calculates the mortgage or annuity payment / yield per 
     * period. 
     *  
     * @param r 
     *            - periodic interest rate represented as a decimal. 
     * @param nper 
     *            - number of total payments / periods. 
     * @param pv 
     *            - present value -- borrowed or invested principal. 
     * @param fv 
     *            - future value of loan or annuity. 
     * @param type 
     *            - when payment is made: beginning of period is 1; end, 0. 
     * @return <code>double</code> representing periodic payment amount. 
     */ 
    public static double pmt(double r, int nper, double pv, double fv, int type) { 
            if (r == 0) return -(pv + fv)/nper; 
             
            // i.e., pmt = r / ((1 + r)^N - 1) * -(pv * (1 + r)^N + fv) 
                double pmt = r / (Math.pow(1 + r, nper) - 1) 
                                * -(pv * Math.pow(1 + r, nper) + fv); 
 
                // account for payments at beginning of period versus end. 
                if (type == 1) 
                        pmt /= (1 + r); 
 
                // return results to caller. 
                return pmt; 
        } 
 
        /** 
     * Overloaded pmt() call omitting type, which defaults to 0. 
     *  
     * @see #pmt(double, int, double, double, int) 
     */ 
        public static double pmt(double r, int nper, double pv, double fv) { 
                return pmt(r, nper, pv, fv, 0); 
        } 
 
        /** 
     * Overloaded pmt() call omitting fv and type, which both default to 0. 
     *  
     * @see #pmt(double, int, double, double, int) 
     */ 
        public static double pmt(double r, int nper, double pv) { 
                return pmt(r, nper, pv, 0); 
        } 
 
        /** 
     * Emulates Excel/Calc's FV(interest_rate, number_payments, payment, PV, 
     * Type) function, which calculates future value or principal at period N. 
     *  
     * @param r 
     *            - periodic interest rate represented as a decimal. 
     * @param nper 
     *            - number of total payments / periods. 
     * @param c 
     *            - periodic payment amount. 
     * @param pv 
     *            - present value -- borrowed or invested principal. 
     * @param type 
     *            - when payment is made: beginning of period is 1; end, 0. 
     * @return <code>double</code> representing future principal value. 
     */ 
    public static double fv(double r, int nper, double c, double pv, int type) { 
            if (r==0) return pv; 
             
            // account for payments at beginning of period versus end. 
            // since we are going in reverse, we multiply by 1 plus interest rate. 
                if (type == 1) 
                        c *= (1 + r); 
 
                // fv = -(((1 + r)^N - 1) / r * c + pv * (1 + r)^N); 
                double fv = -((Math.pow(1 + r, nper) - 1) / r * c + pv 
                                * Math.pow(1 + r, nper)); 
 
                // return results to caller. 
                return fv; 
        } 
 
        /** 
     * Overloaded fv() call omitting type, which defaults to 0. 
     *  
     * @see #fv(double, int, double, double, int) 
     */ 
        public static double fv(double r, int nper, double c, double pv) { 
                return fv(r, nper, c, pv, 0); 
        } 
 
        /** 
     * Emulates Excel/Calc's IPMT(interest_rate, period, number_payments, PV, 
     * FV, Type) function, which calculates the portion of the payment at a 
     * given period that is the interest on previous balance. 
     *  
     * @param r 
     *            - periodic interest rate represented as a decimal. 
     * @param per 
     *            - period (payment number) to check value at. 
     * @param nper 
     *            - number of total payments / periods. 
     * @param pv 
     *            - present value -- borrowed or invested principal. 
     * @param fv 
     *            - future value of loan or annuity. 
     * @param type 
     *            - when payment is made: beginning of period is 1; end, 0. 
     * @return <code>double</code> representing interest portion of payment. 
     *  
     * @see #pmt(double, int, double, double, int) 
     * @see #fv(double, int, double, double, int) 
     */ 
        public static double ipmt(double r, int per, int nper, double pv,double fv, int type) { 
 
                // Prior period (i.e., per-1) balance times periodic interest rate. 
            // i.e., ipmt = fv(r, per-1, c, pv, type) * r 
            // where c = pmt(r, nper, pv, fv, type) 
                double ipmt = fv(r, per - 1, pmt(r, nper, pv, fv, type), pv, type) * r; 
 
                // account for payments at beginning of period versus end. 
                if (type == 1) 
                        ipmt /= (1 + r); 
 
                return ipmt; 
        } 
 
        /** 
     * Emulates Excel/Calc's PPMT(interest_rate, period, number_payments, PV, 
     * FV, Type) function, which calculates the portion of the payment at a 
     * given period that will apply to principal. 
     *  
     * @param r 
     *            - periodic interest rate represented as a decimal. 
     * @param per 
     *            - period (payment number) to check value at. 
     * @param nper 
     *            - number of total payments / periods. 
     * @param pv 
     *            - present value -- borrowed or invested principal. 
     * @param fv 
     *            - future value of loan or annuity. 
     * @param type 
     *            - when payment is made: beginning of period is 1; end, 0. 
     * @return <code>double</code> representing principal portion of payment. 
     *  
     * @see #pmt(double, int, double, double, int) 
     * @see #ipmt(double, int, int, double, double, int) 
     */ 
        public static double ppmt(double r, int per, int nper, double pv, double fv, int type) { 
 
                // Calculated payment per period minus interest portion of that period. 
            // i.e., ppmt = c - i 
            // where c = pmt(r, nper, pv, fv, type) 
            // and i = ipmt(r, per, nper, pv, fv, type) 
        	return pmt(r, nper, pv, fv, type) - ipmt(r, per, nper, pv, fv, type); 
    } 
        
        
        /**  
         * 实际利率法  
         * @author Bean(mailto:mailxbs@126.com)  
         * @param a 现值  
         * @param b 年金  
         * @param c 期数  
         * @param cnt 运算次数  
         * @param ina 误差位数  
         * @return 利率  
         */  
        public static double rate(double a,double b,double c,int cnt,int ina){   
            double rate = 1,x,jd = 0.1,side = 0.1,i = 1;   
            do{   
                x = a/b - (Math.pow(1+rate, c)-1)/(Math.pow(rate+1, c)*rate);   
                if(x*side>0){side = -side;jd *=10;}   
                rate += side/jd;   
            }while(i++<cnt&&Math.abs(x)>=1/Math.pow(10, ina));   
            if(i>cnt)return Double.NaN;   
            return rate;   
        } 
    
    public static void main(String[] args) {
    	//System.out.println(ppmt(new Double(0.0046),21,24,new Double(9963.54),new Double(0),0));
    	//System.out.println(ipmt(new Double(0.0046),1,24,new Double(9963.54),new Double(0),0));
    	System.out.println(StringOperator.round(rate(new Double(9963.54),new Double(439.44),24,200,10),4));
    	//System.out.println(new Double(9963.54)*new Double(0.0046)*(Math.pow((1+0.0046),24)-Math.pow((1+0.0046),(21-1)))/(Math.pow((1+0.0046),24)-1));
    	//double monthinterestmoney=new Double(9963.54)*new Double(0.0046)*(Math.pow((1+0.0046),24)-Math.pow((1+0.0046),(1-1)))/(Math.pow((1+0.0046),24)-1);
    	java.text.DecimalFormat df =new java.text.DecimalFormat("#.00"); 
    	float a=40.37549578077541f;
    	//System.out.println(df.format(a));
    	float avprice = 234.5678f;

    //	java.text.DecimalFormat df =new java.text.DecimalFormat("#.00"); 

    	double b=10000;
    	double c=0;
    	double v=0;
        for(int i=0;i<12;i++){
	    	
	    	v=StringOperator.round(b*0.194/12,2);
	    	c=StringOperator.sub(439.44, 275);
	    	c=StringOperator.sub(c, v);
	    	b=StringOperator.sub(b, c);
	    	System.out.println("本金=="+c);  //234.56
	        System.out.println("b=="+b);  //234.56
        }
    	
 
    	for(int i=1;i<=24;i++){
    		System.out.println(StringOperator.round(-ppmt(new Double(0.0046),i,24,new Double(9963.61),new Double(0),0),2));
    		//double b=-ppmt(new Double(0.015),i,24,new Double(102748.40),new Double(0),0);
    		
    		
    		//System.out.println(b);
    	}
    	System.out.println(StringOperator.round(9985.68*0.194/12,2));  //234.56
 /*   	//System.out.println(rate(现值,年金,期数,200,10));   
 
    	for(int i=1;i<=18;i++){
    	//每月利息额   
        double monthinterestmoney=new Double(9963.54)*new Double(0.0046)*(Math.pow((1+0.0046),24)-Math.pow((1+0.0046),(i-1)))/(Math.pow((1+0.015),24)-1);
        //每月还款额   
        double monthmoney=new Double(10850)*new Double(0.0175)*(Math.pow((1+0.0175),18))/(Math.pow((1+0.0175),18)-1);   
                       
        //每月本金             
        double principalmoney=monthmoney-monthinterestmoney;   
        //实际每月利率   
        double monthacive=monthinterestmoney/monthmoney;  
        if(i==7){
        System.out.println("principalmoney=="+principalmoney);
        System.out.println("monthinterestmoney=="+monthinterestmoney);
        System.out.println("monthmoney=="+monthmoney);
        System.out.println("monthmoney111=="+StringOperator.add(monthmoney, new Double(81.38)));
        }
    
        
    	}*/
    }
}
