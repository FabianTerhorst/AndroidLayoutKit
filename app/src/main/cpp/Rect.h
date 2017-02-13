//
// Created by Fabian Terhorst on 13.02.17.
//

#ifndef LAYOUTKIT_RECT_H
#define LAYOUTKIT_RECT_H


#include "Size.h"
#include "Point.h"

class Rect {
public:
    Point origin;
    Size size;

    Rect(Point origin, Size size) : origin(origin), size(size) {};

    Rect(double x, double y, double width, double height) : origin(Point(x, y)), size(Size(width, height)) {};

    void offsetBy(float x, float y);
};


#endif //LAYOUTKIT_RECT_H
