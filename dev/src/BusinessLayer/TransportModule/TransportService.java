package src.BusinessLayer.TransportModule;

public class TransportService {
        Pool pool;
        public TransportService(){
           // pool = pool.getInstance();
        }
        public boolean hasTransport(int brunchId, String date) {  return Pool.getInstance().hasTransport(brunchId, date); }

        public String getStringRequest(int orderId) { return Pool.getInstance().getReqString(orderId); }


    }


