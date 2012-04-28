package rppascanner

class Spot {

    double FG
    double BG
    int x
    int y
    int diameter
    int flag
    int block
    int row
    int column

    static constraints = {
    }

    String toString()
    {
        "B/C/R: ${block}/${column}/${row}, FG/BG: ${FG}/${BG}, flag: ${flag}"
    }
}
