//package battleship;
//
//public class CoordinateHandler {
//    private String rawCoordinates;
//    private boolean isValidCoordinates;
//    private String errorType ;
//
//    public boolean hasEnteredValidCoordinates() {
//        return isValidCoordinates;
//    }
//
//    public String getErrorType() {
//        return errorType;
//    }
//
//    public void transferUserInputCoordinatesInTheTranslator(String strCoordinates) {
//        this.rawCoordinates = strCoordinates;
//        analyse();
//    }
//
//    private void analyse() {
//       String[] splitCoordinates = rawCoordinates.split(" ");
//       if (splitCoordinates.length != 2) {
//            errorType = "Error! Please input 2 coordinates separated by a String";
//            return;
//       }
//       //check for both coordinates
//        for (String coordinateStr : splitCoordinates ) {
//            //split the coordinateStr into 2 parts : row and column part
//            String[] splitCoordinateStr = coordinateStr.split("",2);
//            //is the first part a letter A to J ?
//            int column = (int) splitCoordinateStr[0].charAt(0) - 65;
//            //logic to check the row
//            int row = Integer.valueOf(splitCoordinateStr[1]);
//            if (column < 1 || column > 10 || row < 1 || row > 10) {
//                errorType = "Error! Wrong ship location. Try again:";
//                return;
//            }
//
//
//            }
//        }
//    }
//
//
//}
