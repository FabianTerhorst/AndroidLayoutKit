//
// Created by Fabian Terhorst on 13.02.17.
//

#ifndef LAYOUTKIT_EDGEINSETS_H
#define LAYOUTKIT_EDGEINSETS_H


class EdgeInsets {
public:
    double top, left, bottom, right;

    EdgeInsets(double top, double left, double bottom, double right) : top(top), left(left), bottom(bottom), right(right) {};
};


#endif //LAYOUTKIT_EDGEINSETS_H
