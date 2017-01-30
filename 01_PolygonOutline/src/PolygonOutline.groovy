class Point{
    private double x
    private double y

    Point(def x, def y){
        this.x = x
        this.y = y
    }

    String toString(){
        "[x=$x, y=$y]"
    }

    boolean equals (Object other){
        if (null == other) return false
        if (! (other instanceof Point)) return false
        return ((x == other.x) && (y == other.y))
    }

    int hashCode(){
        return this.toString().hashCode()
    }

    int compare(Point p){
       if ((this.x <=> p.x) == 0) {
           return (this.y <=> p.y)
       } else {
           return (this.x <=> p.x)
       }
    }

    boolean outOfBorderC(Point pointB, Point pointC){
        /* Вычисляем направление минимального угла между векторами AB и AC.
        *  Фактически, это положение точки С относительно вектора AB.
        *  Если значение положительно, значит точка С слева, если отрицательно - справа.
        *  Так как мы строим контур по часовой стрелке, то отрицательное значение turn
        *  говорит нам что точка за пределом контура */

        double turn = (pointB.x - this.x)*(pointC.y - this.y) - (pointB.y - this.y)*(pointC.x - this.x)

        if (turn == 0){
            /* Точка С лежит на прямой AB (после B). Считаем, что она за контуром, только
             * если AC длиннее AB */
            return (((pointB.x - this.x)**2 + (pointB.y - this.y)**2) < ((pointC.x - this.x)**2 + (pointC.y - this.y)**2))
        } else {
            return (turn > 0)
        }
    }

    /* Ищем следующую точку контура от последней. Так как многоугольник выпуклый,
     * все остальные точки должны по одну сторону вектора. Так как обход по часовой
      * стрелке, то все остальные точки должны лежать "справа" */
    Point findNextPoint(List<Point> points){
        for(pointB in points){
            if (pointB == this) continue
            boolean outOfBorder = false
            for (pointC in points){
                if ((pointC == this) || (pointC == pointB)) continue
                outOfBorder = outOfBorder || this.outOfBorderC(pointB, pointC)
                if (outOfBorder) break
            }
            if (!outOfBorder) return pointB
        }
        return this
    }
}

def fillBorder = { points, border ->
    /* Отбрасываем дубли точек */
    points = points.toSet().toList();
    /* Получение первой точки контура (наименьшая по осям XY всегда на контуре) */
    border.add(points.min { a, b -> a.compare(b) })
    while(true)
    {
        border.add(border.last().findNextPoint(points))
        if (border.first() == border.last()) {
            /* Если контру замкнулся (вернулись к первой точке), то удаляем ее дубль и завершаем работу */
            border.remove(border.size()-1)
            break
        };
    }
}

List<Point> border = []
List<Point> points = [ new Point(1,2), new Point(-6,-1), new Point(-4,2), new Point(4,-1), new Point(3,2), new Point(2,1), new Point(5,4),
                       new Point(-1,4), new Point(-1,6), new Point(-4,5), new Point(-1,-1)]
assert points.size() > 0, "List of points must be not null"

fillBorder(points, border)

println points
println border

