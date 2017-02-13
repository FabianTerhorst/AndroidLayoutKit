//
// Created by Fabian Terhorst on 13.02.17.
//

#ifndef LAYOUTKIT_SIZE_H
#define LAYOUTKIT_SIZE_H


#include "EdgeInsets.h"

class Size {

public:
    double width, height;

    Size(double width, double height) : width(width), height(height) {};

    Size decreaseBySize(Size *maxSize);

    Size increaseBySize(Size *minSize);

    Size decreaseByInsets(EdgeInsets *insets);

    Size increaseByInsets(EdgeInsets *insets);
};


#endif //LAYOUTKIT_SIZE_H
